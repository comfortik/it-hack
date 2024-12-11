package com.example.it_tech_hack.data.repositoryImpl

import com.example.it_tech_hack.data.models.StockData
import com.example.it_tech_hack.data.models.StockResponse
import com.example.it_tech_hack.data.sources.api.StockApiService
import com.example.it_tech_hack.domain.repositories.StockRemoteRepository
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class StockRemoteRepositoryImpl(
    private val stockApiService: StockApiService
): StockRemoteRepository {
    override suspend fun getStockData(): Result<Map<String, List<StockData>>> {
        return try {
            val todayDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            val threeDaysAgoDate = LocalDateTime.now().minusDays(100).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))


            val response = stockApiService.getStockData(
                symbols = "AAPL,MSFT,AMZN,GOOGL,TSLA,META,NVDA,JNJ",
            interval = "1day",
                startDate = threeDaysAgoDate,
                endDate = todayDate
            )

            if (response.isSuccessful) {
                val stockResponse = response.body()

                // Проверяем, что данные получены и распарсим их
                if (stockResponse != null) {
                    val result = mutableMapOf<String, List<StockData>>()

                    stockResponse.AAPL?.let { result["AAPL"] = it.values }
                    stockResponse.GOOGL?.let { result["GOOGL"] = it.values }
                    stockResponse.AMZN?.let { result["AMZN"] = it.values }
                    stockResponse.MSFT?.let { result["MSFT"] = it.values }
                    stockResponse.TSLA?.let { result["TSLA"] = it.values }
                    stockResponse.META?.let { result["META"] = it.values }
                    stockResponse.NVDA?.let { result["NVDA"] = it.values }
                    stockResponse.BRK_B?.let { result["BRK.B"] = it.values }
                    stockResponse.JNJ?.let { result["JNJ"] = it.values }
                    stockResponse.V?.let { result["V"] = it.values }
                    stockResponse.NFLX?.let { result["NFLX"] = it.values }
                    stockResponse.DIS?.let { result["DIS"] = it.values }
                    stockResponse.PYPL?.let { result["PYPL"] = it.values }
                    stockResponse.INTC?.let { result["INTC"] = it.values }
                    stockResponse.AMD?.let { result["AMD"] = it.values }
                    stockResponse.PEP?.let { result["PEP"] = it.values }
                    stockResponse.BA?.let { result["BA"] = it.values }
                    stockResponse.WMT?.let { result["WMT"] = it.values }
                    stockResponse.MCD?.let { result["MCD"] = it.values }

                    Result.success(result)
                } else {
                    Result.failure(Exception("Response body is null"))
                }
            } else {
                Result.failure(Exception("Failed to load stock data"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }



}
