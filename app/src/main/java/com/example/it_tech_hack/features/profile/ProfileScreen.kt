package com.example.it_tech_hack.features.profile

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
            Text("Profile", style = MaterialTheme.typography.headlineLarge)
            Spacer(Modifier.weight(1f))
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "Settings",
                modifier = Modifier
                    .clickable { /* TODO: Show dropdown menu */ }
            )
        }

        Spacer(Modifier.height(16.dp))
        Row(
            Modifier.padding(vertical = 12.dp)
        ) {
            Text("${state.value.userMoney}₽", style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp))
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
            Text("Add 0.5 ₽", color = Color.White)
        }

        Spacer(Modifier.height(16.dp))

        if (state.value.isLoading) {
            CircularProgressIndicator()
        } else {
            Text("Top 3 Stocks", style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp))
            Spacer(Modifier.height(8.dp))
            state.value.topStocks.forEach { stock ->
                Text(stock, style = MaterialTheme.typography.bodyLarge)
                Spacer(Modifier.height(4.dp))
            }
        }
    }
}
