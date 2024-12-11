package com.example.it_tech_hack.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Stock (
    val id: Int?=0,
    val uid: String,
    val type_invest: Int,
    val symbol: String,
    val count: Int
)