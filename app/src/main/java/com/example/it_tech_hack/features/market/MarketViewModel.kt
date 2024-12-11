package com.example.it_tech_hack.features.market

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.hz.common.BaseAction
import com.example.hz.common.BaseIntent
import com.example.hz.common.BaseViewModel
import com.example.it_tech_hack.domain.repositories.BondsRepository
import com.example.it_tech_hack.domain.repositories.CurrencyRepository
import com.example.it_tech_hack.domain.repositories.StockRemoteRepository
import com.example.it_tech_hack.features.market.models.MarketIntent
import com.example.it_tech_hack.features.market.models.MarketState
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MarketViewModel(
    val currencyRepository: CurrencyRepository,
    private val stockRemoteRepository: StockRemoteRepository,
    private val  bondsRepository: BondsRepository

): BaseViewModel<MarketState, BaseAction, MarketIntent> (){
    override fun createInitialState(): MarketState =
        MarketState()

    override fun handleIntent(intent: MarketIntent) {
        when(intent){
            is MarketIntent.OnFilterChanged->{
                _state.value = state.copy(
                    filter = intent.filter
                )
            }
            is MarketIntent.OnSortTypeChanged->{
                _state.value = state.copy(
                    sort =  intent.sortType
                )
            }

            MarketIntent.OnDropDownMenuClicked -> {
                _state.value = state.copy(
                    isDropDownVisible = state.isDropDownVisible.not()
                )
            }
        }
    }


    init{
        viewModelScope.launch {
            val stocksList = async { stockRemoteRepository.getStockData() }
            val currenciesList = async { currencyRepository.getCurrencyList() }
            val bonds = async { bondsRepository.getBonds() }

            val stocks = stocksList.await()
            val currencies = currenciesList.await()
            val bo = bonds.await()
            Log.d("bo", bo.toString())

            stocks.onSuccess {
                _state.value = state.copy(stocks = it, isLoading = false, currencies = currencies, bonds = bo)
            }.onFailure {
                _state.value = state.copy(isLoading = false)
            }
        }
    }
}