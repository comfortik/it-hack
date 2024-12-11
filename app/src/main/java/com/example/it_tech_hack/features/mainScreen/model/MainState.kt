package com.example.it_tech_hack.features.mainScreen.model

import com.example.hz.common.BaseState
import com.example.it_tech_hack.R

data class MainState(
    val isLoading: Boolean = false,
    val cost: Double = 0.0,
    val difference: Double= 0.0,
    val percent: Double =0.0,
    val userMoney: Double=0.0,
    val bestStock: StockInfo = StockInfo(
        name = "Reno",
        icon = R.drawable.ic_profile,
        cost = 12.5,
        percent = 1.4
    )
): BaseState
