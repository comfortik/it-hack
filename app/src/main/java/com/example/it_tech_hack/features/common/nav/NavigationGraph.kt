package com.example.it_tech_hack.features.common.nav

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.hz.features.firebase.firebaseAuthWithEmail.FirebaseAuthWithEmailScreen
import com.example.it_tech_hack.features.mainScreen.MainScreen

@Composable
fun NavigationGraph(navController: NavHostController, modifier: Modifier = Modifier, startDestination: Routes) {
    NavHost(navController = navController, startDestination = startDestination.route, modifier = modifier) {
        composable(Routes.Auth.route) {
            FirebaseAuthWithEmailScreen{
                navController.navigate(Routes.Main.route)
            }
        }
        composable(Routes.Market.route) { MarketScreen() }
        composable(Routes.Main.route) { MainScreen(
            onBriefcaseClicked = {navController.navigate(Routes.Briefcase.route)},
            onMarketClicked = {navController.navigate(Routes.Market.route)}
        ) }
        composable(Routes.Profile.route) { ProfileScreen() }
        composable(Routes.Briefcase.route) { BriefcaseScreen() }
        composable(Routes.Settings.route) { SettingsScreen() }
        composable(Routes.StockInfo.route) { StockInfoScreen() }
    }
}



@Composable
fun MarketScreen() {
    Text("Market Screen")
}

@Composable
fun ProfileScreen() {
    Text("Profile Screen")
}

@Composable
fun BriefcaseScreen() {
    Text("Briefcase Screen")
}

@Composable
fun SettingsScreen() {
    Text("Settings Screen")
}

@Composable
fun StockInfoScreen() {
    Text("Stock Info Screen")
}
