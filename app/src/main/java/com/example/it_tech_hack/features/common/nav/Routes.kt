package com.example.it_tech_hack.features.common.nav

sealed class Routes(val route: String) {
    data object Market : Routes("market")
    data object Main : Routes("main")
    data object Profile : Routes("profile")
    data object Briefcase : Routes("briefcase")
    data object Settings : Routes("settings")
    data object StockInfo : Routes("stockInfo")
    data object Auth : Routes("auth")
}
