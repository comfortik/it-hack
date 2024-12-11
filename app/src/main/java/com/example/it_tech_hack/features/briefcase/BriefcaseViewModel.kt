package com.example.it_tech_hack.features.briefcase

import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hz.common.BaseAction
import com.example.hz.common.BaseIntent
import com.example.hz.common.BaseViewModel
import com.example.it_tech_hack.domain.models.Briefcase
import com.example.it_tech_hack.domain.models.InvestmentType
import com.example.it_tech_hack.domain.models.Stock
import com.example.it_tech_hack.domain.models.toScreenText
import com.example.it_tech_hack.domain.models.types
import com.example.it_tech_hack.domain.repositories.CurrencyRepository
import com.example.it_tech_hack.domain.repositories.StockRepository
import com.example.it_tech_hack.domain.useCases.GetBriefcaseCostUseCase
import com.example.it_tech_hack.features.briefcase.models.BriefcaseState
import kotlinx.coroutines.launch

class BriefcaseViewModel(
    private val stockRepository: StockRepository,
    private val currencyRepository: CurrencyRepository
) : BaseViewModel<BriefcaseState, BaseAction, BaseIntent>() {

    override fun createInitialState(): BriefcaseState = BriefcaseState()

    override fun handleIntent(intent: BaseIntent) {
    }

    private fun getUsersBriefcase() {
        viewModelScope.launch {
            val briefcase = stockRepository.subscribeBriefcase().collect {
                val priceData = calculateInvestmentsForSymbols(it)
                val percentageData = calculateInvestmentsPercentage(it)
                updateStateWithPriceAndPercentageData(priceData, percentageData)
            }
        }
    }

    init {
        getUsersBriefcase()
    }

    private suspend fun calculateInvestmentsForSymbols(briefcase: List<Stock>): List<Pair<String, Double>> {
        val totalStocks = briefcase.sumOf { it.count }

        return if (totalStocks > 0) {
            briefcase
                .groupBy { it.symbol }  // Группируем по символам акций
                .map { (symbol, stocks) ->
                    val typeCount = stocks.sumOf { it.count }
                    val currentPrice = currencyRepository.getOnePrice(symbol) // Получаем цену для символа
                    val totalPrice = typeCount * currentPrice // Считаем стоимость актива
                    symbol to totalPrice  // Вместо типа передаем символ
                }
        } else {
            emptyList()
        }
    }

    private fun getInvestmentType(symbol: String): InvestmentType {
        return when (symbol) {
            "USD", "EUR" -> InvestmentType.Currency
            "AAPL", "GOOG" -> InvestmentType.Stocks
            "BND" -> InvestmentType.Bonds
            "GOLD" -> InvestmentType.Gold
            else -> InvestmentType.Currency
        }
    }

    private fun calculateInvestmentsPercentage(briefcase: List<Stock>): List<Pair<InvestmentType, Double>> {
        // Сумма всех инвестиций
        val totalInvestmentValue = briefcase.sumOf { it.count * 1.0 }

        // Группируем по type_invest и считаем стоимость для каждого типа инвестиций
        val investmentSums = briefcase
            .groupBy { it.type_invest }  // Группируем по type_invest
            .map { (typeInvest, stocks) ->
                val totalPrice = stocks.sumOf { it.count * 1.0 }  // Суммируем стоимость акций для этого типа
                val investmentType = InvestmentType.fromInt(typeInvest)  // Получаем тип инвестиций из type_invest
                investmentType to totalPrice  // Возвращаем пару: тип актива и общая стоимость
            }

        // Рассчитываем проценты для каждого типа актива
        return investmentSums.map { (investmentType, totalPrice) ->
            val percentage = (totalPrice / totalInvestmentValue) * 100
            investmentType to percentage  // Возвращаем пару: тип актива и его процент
        }
    }



    private fun updateStateWithPriceAndPercentageData(
        priceData: List<Pair<String, Double>>,
        percentageData: List<Pair<InvestmentType, Double>>
    ) {
        _state.value = state.copy(
            priceData = priceData,
            percentageData = percentageData
        )
    }


}




