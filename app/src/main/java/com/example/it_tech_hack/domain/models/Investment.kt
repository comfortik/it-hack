package com.example.it_tech_hack.domain.models

data class Investment (
    val symbol: String,
    val capitalize: Double = 0.0,
    val growth: Double,
    val cost: Double,
    val type: InvestmentType
)

