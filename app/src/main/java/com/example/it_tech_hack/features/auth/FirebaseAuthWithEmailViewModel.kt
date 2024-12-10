package com.example.hz.features.firebase.firebaseAuthWithEmail

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.hz.common.BaseViewModel
import com.example.hz.features.firebase.firebaseAuthWithEmail.models.FirebaseAuthWithEmailAction
import com.example.hz.features.firebase.firebaseAuthWithEmail.models.FirebaseAuthWithEmailIntent
import com.example.hz.features.firebase.firebaseAuthWithEmail.models.FirebaseAuthWithEmailState
import com.example.it_tech_hack.data.sources.SharedPrefsProvider
import com.example.it_tech_hack.domain.repositories.AuthRepository
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.coroutines.launch

class FirebaseAuthWithEmailViewModel(
    private val authRepository: AuthRepository
): BaseViewModel<FirebaseAuthWithEmailState, FirebaseAuthWithEmailAction, FirebaseAuthWithEmailIntent>(){

    override fun createInitialState()=
        FirebaseAuthWithEmailState()

    override fun handleIntent(intent: FirebaseAuthWithEmailIntent) {
        when(intent){
            is FirebaseAuthWithEmailIntent.Auth->{
                authWithEmailAndPassword(state.email, state.password)
            }
            is FirebaseAuthWithEmailIntent.ChangeEmail->{
                _state.value = state.copy(
                    email = intent.email
                )
            }
            is FirebaseAuthWithEmailIntent.ChangePassword->{
                _state.value = state.copy(
                    password = intent.password
                )
            }
            is FirebaseAuthWithEmailIntent.SignIn->{
                signInWithEmailAndPassword(state.email, state.password)
            }
        }
    }
    private fun authWithEmailAndPassword(email: String, password: String){
        _state.value = state.copy(isLoading = true)
        viewModelScope.launch {
            authRepository.signUp(email, password,
                onComplete = {
                    navigateIntoProfile(it)

                },
                onError = {
                    handleAuthError(it)
                }
            )
        }
    }
    private  fun signInWithEmailAndPassword(email: String, password: String){
        viewModelScope.launch {
            authRepository.signIn(
                email,
                password,
                onComplete = {
                    navigateIntoProfile(it)
                },
                onError = {
                    handleAuthError(it)
                })
        }
    }
    private fun handleAuthError(exception: Exception) {
        when (exception) {
            is FirebaseAuthWeakPasswordException -> {
                _action.tryEmit(FirebaseAuthWithEmailAction.ShowError("invalid password"))
                Log.d("repo", "auth weak password exception")
            }
            is FirebaseAuthInvalidCredentialsException -> {
                _action.tryEmit(FirebaseAuthWithEmailAction.ShowError("invalid email or password"))
                Log.d("repo", "auth invalid credentials ")
            }

            is FirebaseAuthUserCollisionException -> {
                Log.d("viewmodel", "Trying to emit error message")

                // Логируем размер кэша перед эмитом
                Log.d("viewmodel", "Replay cache size: ${_action.replayCache.size}")

                // Логируем количество активных подписчиков
                Log.d("viewmodel", "Number of active collectors: ${_action.subscriptionCount.value}")

                // Попытка эмитировать
                val success = _action.tryEmit(FirebaseAuthWithEmailAction.SignIn)

                // Логируем результат попытки эмита
                Log.d("viewmodel", "Emit result: $success")

                // Логируем размер кэша и количество подписчиков после эмита
                Log.d("viewmodel", "Replay cache size after emit: ${_action.replayCache.size}")
                Log.d("viewmodel", "Number of active collectors after emit: ${_action.subscriptionCount.value}")
            }

            is FirebaseAuthEmailException -> {
                Log.d("repo", "auth email")
            }
            is FirebaseAuthInvalidUserException -> {
                Log.d("repo", "auth invalid user")
            }
            else -> {
                Log.d("repo", exception.message.toString())
            }
        }
    }
    private fun navigateIntoProfile(userId: String){
        SharedPrefsProvider.setSharedPrefs(USER_ID, userId)
        _action.tryEmit(FirebaseAuthWithEmailAction.NavigateToProfile(userId))
    }
    companion object{
        const val USER_ID = "USER_ID"
    }

}