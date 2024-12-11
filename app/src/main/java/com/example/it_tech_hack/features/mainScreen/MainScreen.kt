package com.example.it_tech_hack.features.mainScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollSource.Companion.SideEffect
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.it_tech_hack.R
import com.example.it_tech_hack.features.common.CardItem
import com.example.it_tech_hack.features.mainScreen.model.MainIntent
import org.koin.androidx.compose.koinViewModel


@Composable
fun MainScreen (
    viewModel: MainScreenViewModel= koinViewModel(),
    buySymbol: String? = null,
    type: Int? = null,
    onBriefcaseClicked: ()->Unit,
    onMarketClicked: ()->Unit
){


    val state = viewModel.screenState.collectAsState()


    LaunchedEffect(buySymbol, type) {
        if (buySymbol != null && type != null) {
            viewModel.handleIntent(MainIntent.BuyInvestment(buySymbol, type))
        }
    }

    Column(
        Modifier.fillMaxSize().padding(12.dp).
        padding(vertical = 12.dp)
    ){
        CardItem(
            content = {MyBriefcaseCard(
                modifier = Modifier.padding(12.dp),
                cost = state.value.cost,
                difference = state.value.difference,
                percent = state.value.percent,
                onClick = onBriefcaseClicked
            )}
        )
        Spacer(Modifier.height(16.dp))
        CardItem(
            content = {
                StokeMarketCard(modifier = Modifier.padding(16.dp))
            },
            onClick = onMarketClicked
        )



    }
}

@Composable
fun StokeMarketCard(
    modifier: Modifier
){
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column (
            modifier=modifier
        ){
            Text(text = "Фондовый рынок",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 22.sp
                )
            )
            Spacer(Modifier.height(8.dp))
            Text(text = "Посмотреть мои предложения",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 12.sp
                ))
        }
        Spacer(Modifier.weight(1f))
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = null
        )
        Spacer(Modifier.width(12.dp))
    }
}


@Composable
fun MyBriefcaseCard(
    cost: Double ,
    difference: Double ,
    percent: Double,
    modifier: Modifier = Modifier.padding(horizontal = 12.dp),
    onClick: ()->Unit
){

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
            .background(color = Color.DarkGray)
            .padding(12.dp)
        ,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_main),
            contentDescription = null
        )
        Spacer(
            modifier = Modifier.width(16.dp)
        )
        Text(
            text = "Мой портфель"
        )
        Spacer(Modifier.weight(1f))
        Icon(
            imageVector =  Icons.Default.KeyboardArrowRight,
            contentDescription = null
        )
    }
    Column(
        modifier = modifier
    ) {
        Text(
            "Стоимость портфеля"
        )
        Text(
            modifier  = Modifier.padding(vertical = 12.dp),
           text=  "₽ $cost",
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 22.sp)
        )
        DifText(difference,percent )
    }

}

@Composable
fun DifText(difference: Double, percent: Double) {
    val formattedDifference = String.format("%.2f", difference)
    val formattedPercent = String.format("%.2f", percent)

    if (difference > 0) {
        Text(
            text = "+ $formattedDifference ($formattedPercent %)",
            style = MaterialTheme.typography
                .bodyLarge.copy(
                    fontSize = 14.sp,
                    color = Color.Green
                )
        )
    } else if (difference < 0) {
        Text(
            text = "$formattedDifference ($formattedPercent %)",
            style = MaterialTheme.typography
                .bodyLarge.copy(
                    fontSize = 14.sp,
                    color = Color.Red
                )
        )
    } else {
        Text(
            text = "",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 14.sp
            )
        )
    }
}
