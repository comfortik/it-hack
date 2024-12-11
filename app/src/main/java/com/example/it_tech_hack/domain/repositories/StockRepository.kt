package com.example.it_tech_hack.domain.repositories

import com.example.it_tech_hack.domain.models.Stock
import kotlinx.coroutines.flow.Flow

interface StockRepository {
    suspend fun subscribeBriefcase(): Flow<List<Stock>>
    suspend fun addNewStock(symbol: String, count: Int=1, type: Int)
    suspend fun updateCountOfSymbol(symbol: String, count: Int)
}