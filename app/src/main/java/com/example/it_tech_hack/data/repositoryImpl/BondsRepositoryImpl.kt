package com.example.it_tech_hack.data.repositoryImpl

import com.example.it_tech_hack.data.models.Bond
import com.example.it_tech_hack.data.sources.api.StockApiService
import com.example.it_tech_hack.domain.repositories.BondsRepository

class BondsRepositoryImpl(
    private val apiService: StockApiService
): BondsRepository {
    override suspend fun getBonds(): List<Bond> {

        return    try {
                val response = apiService.getBonds()
                response.result.list
            } catch (e: Exception) {
                emptyList()
            }

    }
}