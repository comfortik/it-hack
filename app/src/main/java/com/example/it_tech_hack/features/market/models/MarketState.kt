package com.example.it_tech_hack.features.market.models

import android.os.Parcel
import android.os.Parcelable
import com.example.hz.common.BaseState
import com.example.it_tech_hack.data.models.Bond
import com.example.it_tech_hack.data.models.StockData
import com.example.it_tech_hack.domain.models.Investment
import com.example.it_tech_hack.domain.models.InvestmentType
import io.ktor.util.Hash

data class MarketState (
    val isDropDownVisible: Boolean = false,
    val isLoading: Boolean = true,
    val currencies: HashMap<String, Pair<Double, Double>> = hashMapOf(),
    val stocks: Map<String, List<StockData>> = mapOf(),
    val filter: InvestmentType? = null,
    val bonds:  List<Bond> = listOf(),
    val sort: SortInvestment = SortInvestment.Popular
): BaseState


sealed interface SortInvestment{
    data object Popular: SortInvestment
    data  object Expensive: SortInvestment
    data object Cheep: SortInvestment
}