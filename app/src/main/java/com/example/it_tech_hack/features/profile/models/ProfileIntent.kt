package com.example.it_tech_hack.features.profile.models

import com.example.hz.common.BaseIntent

sealed class ProfileIntent: BaseIntent {
    data object IncrementMoney : ProfileIntent()
    data object LoadTopStocks : ProfileIntent()
}