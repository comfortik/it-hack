package com.example.it_tech_hack.data.repositoryImpl

import com.example.it_tech_hack.domain.repositories.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException

class AuthRepositoryImpl(
    private val fAuth: FirebaseAuth
): AuthRepository {

    override suspend fun signUp(email: String, password: String, onComplete: (String)-> Unit, onError: (e: FirebaseAuthException)->Unit) {
        fAuth
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    onComplete(it.result.user?.uid?:"")
                }else{
                    onError(it.exception as FirebaseAuthException)
                }

            }
    }

    override suspend fun signIn(email: String, password: String,  onComplete: (String)-> Unit, onError: (e: FirebaseAuthException)->Unit) {
        fAuth
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    onComplete(it.result.user?.uid?:"")
                }else{
                    onError(it.exception as FirebaseAuthException)
                }
            }
    }




}