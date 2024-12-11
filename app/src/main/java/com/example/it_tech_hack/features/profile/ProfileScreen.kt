package com.example.it_tech_hack.features.profile

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hz.common.BaseViewModel
import com.example.it_tech_hack.features.common.CardItem
import com.example.it_tech_hack.features.mainScreen.MyBriefcaseCard
import com.example.it_tech_hack.features.market.InvestmentCard
import com.example.it_tech_hack.features.profile.models.ProfileIntent
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(
    profileScreenViewModel: ProfileScreenViewModel = koinViewModel(),
    onBriefcaseClick: ()->Unit
) {
    val state = profileScreenViewModel.screenState.collectAsState()


    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Профиль", style = MaterialTheme.typography.headlineLarge)
            Spacer(Modifier.weight(1f))
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "Settings",
                modifier = Modifier
                    .clickable { }
            )
        }

        Spacer(Modifier.height(16.dp))
        Row(
            Modifier.padding(vertical = 12.dp)
        ) {
            val formattedPercent = String.format("%.2f",state.value.userMoney)
            Text("Доступно вам: $formattedPercent ₽", style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp))
        }

        CardItem(
            content = {
                MyBriefcaseCard(
                modifier = Modifier.padding(12.dp),
                cost = state.value.cost,
                difference = state.value.difference,
                percent = state.value.percent,
                onClick = {onBriefcaseClick()}
            )
            }
        )
        Spacer(Modifier.height(16.dp))

        Box(
            Modifier
                .fillMaxWidth()
                .background(Color.DarkGray, shape = RoundedCornerShape(12.dp))
                .clickable {
                    profileScreenViewModel.handleIntent(ProfileIntent.IncrementMoney)
                }
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("Получить 0.5 ₽", color = Color.White)
        }

        Spacer(Modifier.weight(1f))

        LazyColumn {
            items(state.value.bestStock.toList()) { item ->
                InvestmentCard(
                    symbol = item.first,
                    price = item.second.first,
                    dif = item.second.second - item.second.first,
                    onCardClick = { name, price, dif ->

                    }
                )
                Spacer(Modifier.height(12.dp))
            }
        }
        Spacer(Modifier.weight(1f))
    }
}
