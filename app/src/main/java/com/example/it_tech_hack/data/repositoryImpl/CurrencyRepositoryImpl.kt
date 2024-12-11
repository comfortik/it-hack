package com.example.it_tech_hack.data.repositoryImpl

import android.util.Log
import com.example.it_tech_hack.data.models.currencyPairs
import com.example.it_tech_hack.data.sources.api.CurrencyApiService
import com.example.it_tech_hack.domain.repositories.CurrencyRepository
import org.koin.androidx.compose.get
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CurrencyRepositoryImpl(
    private val currencyApiService: CurrencyApiService
) : CurrencyRepository {

    private lateinit var currencies: HashMap<String, Pair<Double, Double>>

    override suspend fun getPriceFromSymbol(symbols: List<String>): HashMap<String, Pair<Double, Double>> {
        if (!::currencies.isInitialized) {
            currencies = getCurrencyList()
        }
        return currencies
    }

    override suspend fun getOnePrice(symbol: String): Double {
        if (!::currencies.isInitialized) {
            currencies = getCurrencyList()
        }
        return currencies[symbol]?.first?:0.0
    }

    override suspend fun getCurrencyList(): HashMap<String, Pair<Double, Double>> {
        if (::currencies.isInitialized) {
            return currencies
        } else {
            val todayDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-dd-MM"))
            val threeDaysAgoDate = LocalDateTime.now().minusDays(100).format(DateTimeFormatter.ofPattern("yyyy-dd-MM"))
            Log.d(todayDate, threeDaysAgoDate)
            val currentRates = currencyApiService.getCurrencies(
                date =  todayDate
            ).data

            val historicalRates = currencyApiService.getCurrencies(
                date = threeDaysAgoDate
            ).data
            Log.d("API Response", "CurrentRates: $currentRates, ")
            Log.d("API Response", "HistoricalRates: $historicalRates ")

             currencies = HashMap(currentRates.map { entry ->
                val key = entry.key
                 val currentRate = 1 / entry.value.value

                 val historicalRateValue = 1/historicalRates[key]?.value!!


                 key to (currentRate to historicalRateValue)
            }.toMap())

            return currencies
        }
    }
}



