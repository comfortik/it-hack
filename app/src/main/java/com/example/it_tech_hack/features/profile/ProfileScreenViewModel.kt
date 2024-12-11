package com.example.it_tech_hack.features.profile

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.hz.common.BaseAction
import com.example.hz.common.BaseIntent
import com.example.hz.common.BaseViewModel
import com.example.it_tech_hack.domain.repositories.UserRepository
import com.example.it_tech_hack.domain.useCases.GetBriefcaseCostUseCase
import com.example.it_tech_hack.features.profile.models.ProfileIntent
import com.example.it_tech_hack.features.profile.models.ProfileState
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class ProfileScreenViewModel(
    private val userRepository: UserRepository,
    private val getBriefcaseCostUseCase: GetBriefcaseCostUseCase
) : BaseViewModel<ProfileState, BaseAction, ProfileIntent>() {

    override fun createInitialState(): ProfileState = ProfileState()

    override fun handleIntent(intent: ProfileIntent) {
        Log.d("inent", intent.toString())
        when (intent) {
            is ProfileIntent.IncrementMoney -> incrementUserMoney()
            is ProfileIntent.LoadTopStocks -> loadTopStocks()
        }
    }

    private fun incrementUserMoney() {
        viewModelScope.launch {
            try {
                userRepository.updateMoney(state.userMoney)
            } catch (e: Exception) {
                Log.e("ProfileVM", "Error incrementing money: ${e.message}")
            }
        }
    }

    private fun loadTopStocks() {
        viewModelScope.launch {
            try {
                val stocks = listOf("Stock A +5%", "Stock B +4.8%", "Stock C +4.5%")
                _state.value = state.copy(topStocks = stocks, isLoading = false)
            } catch (e: Exception) {
                Log.e("ProfileVM", "Error loading top stocks: ${e.message}")
            }
        }
    }
    init {
        viewModelScope.launch {
            getBriefcaseCost()
        }
        viewModelScope.launch {
            userRepository.getUser().collect{
                _state.value= state.copy(
                    userMoney = it?.money?:12.0
                )
            }
        }


    }
    private suspend fun getBriefcaseCost(){
        getBriefcaseCostUseCase(
            onComplete = {
                _state.value = state.copy(
                    isLoading = false,
                    cost = it.newCost,
                    difference = it.difference,
                    percent = it.percent
                )
            }
        )
    }
}
