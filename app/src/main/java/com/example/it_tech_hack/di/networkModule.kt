package com.example.it_tech_hack.di

import com.example.it_tech_hack.data.sources.api.CurrencyApiService
import com.example.it_tech_hack.data.sources.api.StockApiService
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val networkModule = module {
    single(named("CurrencyApi")) {
        Retrofit.Builder()
            .baseUrl("https://api.currencyapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
            .create(CurrencyApiService::class.java)
    }


    single(named("StockApi")) {
        Retrofit.Builder()
            .baseUrl("https://api.twelvedata.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
            .create(StockApiService::class.java)
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Accept", "application/json")
                    .build()
                chain.proceed(request)
            }
            .build()
    }
}

