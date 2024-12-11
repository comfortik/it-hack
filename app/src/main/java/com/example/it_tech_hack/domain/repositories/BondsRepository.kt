package com.example.it_tech_hack.domain.repositories

import com.example.it_tech_hack.data.models.Bond

interface BondsRepository {
    suspend fun getBonds(): List<Bond>
}