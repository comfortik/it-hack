package com.example.it_tech_hack.domain.repositories

import com.example.it_tech_hack.domain.models.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUser(): Flow<User?>
    suspend fun addUser(user: User)
    suspend fun updateMoney(money: Double)
}