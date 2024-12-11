package com.example.it_tech_hack.features.market.models

import com.example.hz.common.BaseIntent
import com.example.it_tech_hack.domain.models.InvestmentType

sealed interface MarketIntent: BaseIntent {
    data class OnFilterChanged(val filter: InvestmentType?): MarketIntent
    data class OnSortTypeChanged(val sortType: SortInvestment): MarketIntent
    data object OnDropDownMenuClicked: MarketIntent
}
