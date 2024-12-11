package com.example.it_tech_hack.domain.repositories

interface CurrencyRepository {
    suspend fun getCurrencyList():HashMap<String, Pair<Double, Double>>
    suspend fun getPriceFromSymbol(symbols: List<String>): HashMap<String, Pair<Double, Double>>
    suspend fun getOnePrice(symbol: String): Double

}
