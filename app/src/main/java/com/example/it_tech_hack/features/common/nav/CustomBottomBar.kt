package com.example.it_tech_hack.features.common.nav

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.it_tech_hack.R

@Composable
fun CustomBottomBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomBarItems = listOf(
        Routes.Market to R.drawable.baseline_show_chart_24,
        Routes.Profile to R.drawable.ic_profile
    )
    val withPx = LocalContext.current.resources.displayMetrics.widthPixels
    val barShape = BarShape(
        offset = withPx / 2f,
        circleRadius = 30.dp,
        cornerRadius = 12.dp,
        circleGap = 5.dp
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(R.color.background_gray))
    ) {
        OutlinedCard(
            shape = barShape,
            modifier = Modifier
                .fillMaxWidth()
                .shadow(30.dp),
            border = BorderStroke(1.dp, Color.DarkGray),
        ) {
            NavigationBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                containerColor = Color.DarkGray,
                contentColor = Color.White
            ) {
                bottomBarItems.forEach { (route, icon) ->
                    val isSelected = currentRoute == route.route
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = icon),
                                contentDescription = null
                            )
                        },
                        selected = isSelected,
                        onClick = { navController.navigate(route.route) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.White,
                            unselectedIconColor = Color.Gray,
                            indicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }
        FloatingActionButton(
            onClick = { navController.navigate(Routes.Main.route) },
            shape = CircleShape,
            containerColor = Color.Magenta,
            modifier = Modifier
                .size(55.dp)
                .align(Alignment.BottomCenter)
                .offset(y = (-45).dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_main),
                tint = Color.White,
                contentDescription = "Main"
            )
        }
    }
}
