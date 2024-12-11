package com.example.it_tech_hack.domain.useCases

import android.util.Log
import com.example.it_tech_hack.domain.models.Briefcase
import com.example.it_tech_hack.domain.models.Stock
import com.example.it_tech_hack.domain.repositories.CurrencyRepository
import com.example.it_tech_hack.domain.repositories.StockRepository

class GetBriefcaseCostUseCase(
    private val stockRepository: StockRepository,
    private val currencyRepository: CurrencyRepository
) {
    operator suspend fun invoke(onComplete:( Briefcase)->Unit){
        stockRepository.subscribeBriefcase().collect { stockList ->
            if(stockList.isNotEmpty()){
                try {
                    val cost = calculateNewCost(stockList)
                    val newCost = cost.first
                    val oldCost = cost.second
                    val difference = newCost-oldCost
                    val percent = (difference / if(oldCost!=0.0)oldCost else 1.0)*100
                    onComplete(Briefcase(newCost, difference, percent))
                }catch (e: Exception){
                    Log.d("ex", e.message.toString())
                }
            }else{
                onComplete(Briefcase(0.0,0.0,0.0))
            }

        }
    }
    private suspend fun calculateNewCost(stockList: List<Stock>): Pair<Double, Double> {
        val symbols = stockList.map { it.symbol }
        val costMap = currencyRepository.getPriceFromSymbol(symbols)
        Log.d("viewmodel", costMap.toString())
        val newCost = stockList.sumOf { stock ->
            stock.count * (costMap[stock.symbol]?.first ?: 0.0)
        }
        val oldCost = stockList.sumOf { stock ->
            stock.count * (costMap[stock.symbol]?.second ?: 0.0)
        }
        return Pair(newCost, oldCost)
    }
}