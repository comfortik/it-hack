package com.example.it_tech_hack.features.profile

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.hz.common.BaseAction
import com.example.hz.common.BaseIntent
import com.example.hz.common.BaseViewModel
import com.example.it_tech_hack.domain.repositories.CurrencyRepository
import com.example.it_tech_hack.domain.repositories.UserRepository
import com.example.it_tech_hack.domain.useCases.GetBriefcaseCostUseCase
import com.example.it_tech_hack.features.profile.models.ProfileIntent
import com.example.it_tech_hack.features.profile.models.ProfileState
import kotlinx.coroutines.launch

class ProfileScreenViewModel(
    private val userRepository: UserRepository,
    private val getBriefcaseCostUseCase: GetBriefcaseCostUseCase,
    private val currencyRepository: CurrencyRepository
) : BaseViewModel<ProfileState, BaseAction, ProfileIntent>() {

    override fun createInitialState(): ProfileState = ProfileState()

    override fun handleIntent(intent: ProfileIntent) {
        when (intent) {
            is ProfileIntent.IncrementMoney -> incrementUserMoney()
            is ProfileIntent.LoadTopStocks -> loadTopStocks()
        }
    }
    private suspend fun getBestStocks() {
        val currencyList = currencyRepository.getCurrencyList()

        if (currencyList.isNotEmpty()) {

            val bestStocks = currencyList.toList().filter{ it.second.second-it.second.first>0 }.shuffled().take(3)


            _state.value = state.copy(bestStock = bestStocks)
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
        viewModelScope.launch {
            getBestStocks()
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
