package com.example.it_tech_hack.data.sources.api

import com.example.it_tech_hack.data.models.BondsResponse
import com.example.it_tech_hack.data.models.StockResponse
import com.example.it_tech_hack.domain.models.popularSymbols
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query



interface StockApiService {

    @GET("time_series")
    suspend fun getStockData(
        @Query("symbol") symbols: String = popularSymbols.joinToString(","),
        @Query("interval") interval: String,
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("apikey") apiKey: String = "e87dbe73dbe44db5986d59e10f6aac91"
    ): Response<StockResponse>

    @GET("bonds")
    suspend fun getBonds(): BondsResponse
}

