package com.example.it_tech_hack.data.sources.api

import android.icu.util.Calendar
import com.example.it_tech_hack.data.models.CurrencyDTO
import com.example.it_tech_hack.data.models.currencyPairs
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CurrencyApiService {
    @GET("/v3/historical")
    suspend fun getCurrencies(
        @Query("apikey") apiKey: String = "cur_live_ccIQyzRNHGY97e8bzLgAJfFe8nql16TZSyucys0P",
        @Query("base_currency") baseCurrency: String = "RUB",
        @Query("date") date: String? = null
    ): CurrencyDTO
}


