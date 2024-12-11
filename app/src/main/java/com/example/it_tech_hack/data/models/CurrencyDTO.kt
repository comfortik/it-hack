package com.example.it_tech_hack.data.models

data class CurrencyDTO(
    val meta: Meta,
    val data: Map<String, CurrencyValue>
)

data class Meta(
    val last_updated_at: String
)

data class CurrencyValue(
    val code: String,
    val value: Double
)
