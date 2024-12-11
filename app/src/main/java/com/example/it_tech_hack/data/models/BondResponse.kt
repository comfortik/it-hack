package com.example.it_tech_hack.data.models

data class Bond(
    val symbol: String,
    val name: String,
    val country: String,
    val currency: String,
    val exchange: String,
    val mic_code: String,
    val type: String
)

data class BondsResponse(
    val result: Result,
    val status: String
)

data class Result(
    val count: Int,
    val list: List<Bond>
)

