package com.example.it_tech_hack.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class User (
    val id: String,
    val money: Double
)