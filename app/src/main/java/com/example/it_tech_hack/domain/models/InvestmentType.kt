package com.example.it_tech_hack.domain.models

sealed class InvestmentType(val type: Int){
    data object Gold: InvestmentType(4)
    data object Currency: InvestmentType(1)
    data object Stocks: InvestmentType(2)
    data object Bonds: InvestmentType(3)
}

fun InvestmentType.toScreenText(): String =
    when(type){
        1-> "Валюта"
        2->"Акции"
        3->"Облигации"
        4->"Золото"
        else -> ""
    }

val types = listOf(
    null,
    InvestmentType.Currency,
    InvestmentType.Stocks,
    InvestmentType.Bonds,
    InvestmentType.Gold,

)