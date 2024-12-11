package com.example.it_tech_hack.features.common.nav

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navArgument
import com.example.hz.features.firebase.firebaseAuthWithEmail.FirebaseAuthWithEmailScreen
import com.example.it_tech_hack.domain.models.InvestmentType
import com.example.it_tech_hack.features.mainScreen.MainScreen
import com.example.it_tech_hack.features.market.MarketScreen
import com.example.it_tech_hack.features.profile.ProfileScreen
import com.example.it_tech_hack.features.stockInfo.BuyInvestmentDialog

@Composable
fun NavigationGraph(navController: NavHostController, modifier: Modifier = Modifier, startDestination: Routes) {
    NavHost(navController = navController, startDestination = startDestination.route, modifier = modifier) {
        composable(Routes.Auth.route) {
            FirebaseAuthWithEmailScreen{
                navController.navigate(Routes.Main.route)
            }
        }
        composable(Routes.Market.route) {
            MarketScreen(
                onCardClick = { name, price, dif, type ->
                    navController.navigate(Routes.BuyInvestment.createRoute(name, price.toFloat(), dif.toFloat(), type))
                }
            )
        }
        composable(Routes.Main.route,
            arguments = listOf(navArgument("symbol") { type = NavType.StringType },navArgument("type") { type = NavType.IntType },)
            ) {
            val symbol = it.arguments?.getString("symbol")
            val type = it.arguments?.getInt("type")
            MainScreen(
                buySymbol = symbol,
                type = type,
            onBriefcaseClicked = {navController.navigate(Routes.Briefcase.route)},
            onMarketClicked = {navController.navigate(Routes.Market.route)}
        ) }
        composable(Routes.Profile.route) { ProfileScreen() }
        composable(Routes.Briefcase.route) { BriefcaseScreen() }
        composable(Routes.Settings.route) { SettingsScreen() }
        composable(Routes.StockInfo.route) {  }
        dialog(
            route = Routes.BuyInvestment.route,
            arguments = listOf(
                navArgument("name") { type = NavType.StringType },
                navArgument("price") { type = NavType.FloatType },
                navArgument("dif") { type = NavType.FloatType },
                navArgument("type") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val price = backStackEntry.arguments?.getFloat("price") ?: 0f
            val dif = backStackEntry.arguments?.getFloat("dif") ?: 0f
            val type = backStackEntry.arguments?.getInt("type") ?: 1

            BuyInvestmentDialog(name = name, price = price.toDouble(), dif = dif.toDouble(), type = type) { symbol, type ->
                navController.navigate(Routes.Main.createRoute(symbol, type))
            }
        }


    }
}


@Composable
fun BriefcaseScreen() {
    Text("Briefcase Screen")
}

@Composable
fun SettingsScreen() {
    Text("Settings Screen")
}


