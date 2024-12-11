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
import com.example.it_tech_hack.features.briefcase.BriefcaseScreen
import com.example.it_tech_hack.features.mainScreen.MainScreen
import com.example.it_tech_hack.features.market.MarketScreen
import com.example.it_tech_hack.features.profile.ProfileScreen
import com.example.it_tech_hack.features.stockInfo.BuyInvestmentDialog

@Composable
fun NavigationGraph(navController: NavHostController, modifier: Modifier = Modifier, startDestination: Routes) {
    NavHost(navController = navController, startDestination = startDestination.route, modifier = modifier) {
        composable(Routes.Auth.route) {
            FirebaseAuthWithEmailScreen{
                navController.navigate(Routes.MainA.route)
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
        composable(Routes.Profile.route) { ProfileScreen(){
            navController.navigate(Routes.Briefcase.route)
        } }
        composable(Routes.MainA.route){
            MainScreen(onBriefcaseClicked = {navController.navigate(Routes.Briefcase.route)},
                onMarketClicked = {navController.navigate(Routes.Market.route)})
        }

        composable(Routes.Briefcase.route) { BriefcaseScreen { name, price ->
                navController.navigate(Routes.SaleInvestment.createRoute(name, price.toFloat()))
            } }
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
        dialog(
            route = Routes.SaleInvestment.route,
            arguments = listOf(
                navArgument("name") { type = NavType.StringType },
                navArgument("price") { type = NavType.FloatType }
            )
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val price = backStackEntry.arguments?.getFloat("price") ?: 0f

            SaleInvestmentDialog(name = name, price = price.toDouble(),
                onConfirm = {symbol, price->
                    navController.navigate(Routes.Main.createRoute(symbol, InvestmentType.Currency.type))
                },
                onDismiss = {
                    navController.popBackStack()
                }
                )
        }




    }
}

@Composable
fun SaleInvestmentDialog(name: String, price: Double, onConfirm:(String, Double)->Unit, onDismiss: () -> Unit) {
    androidx.compose.material3.AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text(text = "Удалить актив?")
        },
        text = {
            Text("Вы уверены, что хотите удалить актив \"$name\" стоимостью \$${"%.2f".format(price)}?")
        },
        confirmButton = {
            androidx.compose.material3.Button(
                onClick = {
                    onConfirm(name, price)
                }
            ) {
                Text("Удалить")
            }
        },
        dismissButton = {
            androidx.compose.material3.Button(onClick = onDismiss) {
                Text("Отмена")
            }
        }
    )
}



@Composable
fun SettingsScreen() {
    Text("Settings Screen")
}


