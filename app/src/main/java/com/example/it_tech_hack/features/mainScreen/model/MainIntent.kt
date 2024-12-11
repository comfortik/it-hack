package com.example.it_tech_hack.features.mainScreen.model

import com.example.hz.common.BaseIntent

sealed interface MainIntent: BaseIntent {
    data class BuyInvestment(val symbol: String, val type: Int): MainIntent
}
