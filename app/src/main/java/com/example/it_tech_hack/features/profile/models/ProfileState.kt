package com.example.it_tech_hack.features.profile.models

import com.example.hz.common.BaseState

data class ProfileState(
    val cost: Double = 0.0,
    val difference: Double= 0.0,
    val percent: Double =0.0,
    val userMoney: Double = 0.0,
    val topStocks: List<String> = emptyList(),
    val isLoading: Boolean = true,
    val bestStock: List<Pair<String, Pair<Double, Double>>> = listOf()
): BaseState
