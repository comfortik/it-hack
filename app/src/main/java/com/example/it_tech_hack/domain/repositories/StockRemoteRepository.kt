package com.example.it_tech_hack.domain.repositories

import com.example.it_tech_hack.data.models.StockData

interface StockRemoteRepository {
    suspend fun getStockData(): Result<Map<String, List<StockData>>>
}
