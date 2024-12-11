package com.example.it_tech_hack.features.common.nav

sealed class Routes(val route: String) {
    data object Market : Routes("market")
    data object Main : Routes("main/{symbol}/{type}") {
        fun createRoute(symbol: String, type: Int): String {
            return "main/$symbol/$type"
        }
    }

    data object Profile : Routes("profile")
    data object Briefcase : Routes("briefcase")
    data object Settings : Routes("settings")
    data object StockInfo : Routes("stockInfo")
    data object Auth : Routes("auth")
    data object BuyInvestment : Routes("buyInvestment/{name}/{price}/{dif}/{type}") {
        fun createRoute(name: String, price: Float, dif: Float, type: Int): String {
            return "buyInvestment/$name/$price/$dif/$type"
        }
    }

}
