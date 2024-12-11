package com.example.it_tech_hack.features.mainScreen

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.hz.common.BaseViewModel
import com.example.it_tech_hack.domain.repositories.CurrencyRepository
import com.example.it_tech_hack.domain.repositories.StockRepository
import com.example.it_tech_hack.domain.repositories.UserRepository
import com.example.it_tech_hack.domain.useCases.GetBriefcaseCostUseCase
import com.example.it_tech_hack.features.mainScreen.model.MainAction
import com.example.it_tech_hack.features.mainScreen.model.MainIntent
import com.example.it_tech_hack.features.mainScreen.model.MainState
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class MainScreenViewModel(
    private val stockRepository: StockRepository,
    private val getBriefcaseCostUseCase: GetBriefcaseCostUseCase,
    private val userRepository: UserRepository,
    private val currencyRepository: CurrencyRepository
): BaseViewModel<MainState, MainAction, MainIntent>() {

    override fun createInitialState(): MainState =
        MainState()

    override fun handleIntent(intent: MainIntent) {
        when(intent){
            is MainIntent.BuyInvestment->{
                viewModelScope.launch {
                    val user = userRepository.getUser().firstOrNull()
                    val price =currencyRepository.getOnePrice(intent.symbol)
                    Log.d("a", price.toString())
                    Log.d("s", state.userMoney.toString())
                    if (state.userMoney> price ){
                        stockRepository.addNewStock(intent.symbol, count = 1, type = intent.type)
                        userRepository.updateMoney(state.userMoney-price)
                    }
                }
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
    private suspend fun getBestStocks() {
        val currencyList = currencyRepository.getCurrencyList()

        if (currencyList.isNotEmpty()) {

            val bestStocks = currencyList.toList().filter{ it.second.second-it.second.first>0 }.shuffled().take(3)

            _state.value = state.copy(bestStock = bestStocks)
        }
    }


    private suspend fun getBriefcaseCost() {
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