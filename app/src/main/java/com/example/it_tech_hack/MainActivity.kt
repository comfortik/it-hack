package com.example.it_tech_hack

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.hz.features.firebase.firebaseAuthWithEmail.FirebaseAuthWithEmailViewModel.Companion.USER_ID
import com.example.it_tech_hack.data.sources.SHARED_PREFS
import com.example.it_tech_hack.data.sources.SharedPrefsProvider
import com.example.it_tech_hack.features.common.nav.CustomBottomBar
import com.example.it_tech_hack.features.common.nav.NavigationGraph
import com.example.it_tech_hack.features.common.nav.Routes
import com.example.it_tech_hack.ui.theme.IttechhackTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SharedPrefsProvider.init(this)
        val sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
        val id: String? = SharedPrefsProvider.getSharedPrefs(USER_ID)
        Log.d("d", id?:"null")
        setContent {
            IttechhackTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                val showBottomBar = currentRoute != Routes.Auth.route

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (showBottomBar) {
                            CustomBottomBar(navController = navController)
                        }
                    }
                ) { innerPadding ->
                    NavigationGraph(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding),
                        startDestination = if (id!=null) Routes.Main else Routes.Auth
                    )
                }
            }
        }
    }
}

