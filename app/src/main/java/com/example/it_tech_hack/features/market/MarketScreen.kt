package com.example.it_tech_hack.features.market

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.it_tech_hack.data.models.Bond
import com.example.it_tech_hack.data.models.StockData
import com.example.it_tech_hack.domain.models.Investment
import com.example.it_tech_hack.domain.models.InvestmentType
import com.example.it_tech_hack.domain.models.toScreenText
import com.example.it_tech_hack.domain.models.types
import com.example.it_tech_hack.features.common.CardItem
import com.example.it_tech_hack.features.mainScreen.DifText
import com.example.it_tech_hack.features.market.models.MarketIntent
import org.koin.androidx.compose.koinViewModel

@Composable
fun MarketScreen(
    viewModel: MarketViewModel = koinViewModel(),
    onCardClick: (String, Double, Double, Int) -> Unit
) {
    val state = viewModel.screenState.collectAsState()

    if (state.value.isDropDownVisible) {
        if (state.value.isDropDownVisible) {
            DropdownMenu(
                expanded = state.value.isDropDownVisible,
                onDismissRequest = {
                    viewModel.handleIntent(MarketIntent.OnDropDownMenuClicked)
                },
                modifier = Modifier
                    .background(Color(0x80000000))
                    .clip(RoundedCornerShape(16.dp))
            ) {
                DropdownMenuItem(onClick = {}, text = { Text(text = "Button 1") })
                DropdownMenuItem(onClick = {}, text = { Text(text = "Button 1") })
                DropdownMenuItem(onClick = {}, text = { Text(text = "Button 1") })
                DropdownMenuItem(onClick = {}, text = { Text(text = "Button 1") })
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 16.dp)
    ) {
        LazyRow {
            items(types) { item ->
                FilterItem(item) {
                    viewModel.handleIntent(MarketIntent.OnFilterChanged(item))
                }
                Spacer(Modifier.width(8.dp))
            }
        }
        if (state.value.filter != null) {
            Row(
                Modifier.padding(vertical = 12.dp)
            ) {
                Spacer(Modifier.weight(1f))
                Icon(modifier = Modifier.clickable { viewModel.handleIntent(MarketIntent.OnDropDownMenuClicked) }, imageVector = Icons.Default.List, contentDescription = null)
            }
        }
        when (state.value.filter) {
            null -> {

                MarketWithoutFiltersContent(
                    modifier = Modifier.weight(1f),
                    onHeaderClick = { viewModel.handleIntent(MarketIntent.OnFilterChanged(it)) },
                    onCardClick = onCardClick,
                    currenciesList = state.value.currencies.toList().filter { it.first in listOf("USD", "EUR", "GBP") },
                    stocksList = state.value.stocks.filter { it.key in listOf("AAPL", "GOOG", "AMZN") },
                    bondsList = state.value.bonds.take(3)
                )
            }
            InvestmentType.Bonds -> {
                BondsColumn(modifier = Modifier.weight(1f),bondsList =  state.value.bonds) { symbol, price, dif, type ->
                    onCardClick(symbol, price, dif, InvestmentType.Bonds.type)
                }
            }
            InvestmentType.Currency -> {
                CurrencyColumn(modifier = Modifier.weight(1f), list =  state.value.currencies.toList(), onCardClick = { name, price, dif ->
                    onCardClick(name, price, dif, InvestmentType.Currency.type)
                })
            }
            InvestmentType.Gold -> {}
            InvestmentType.Stocks -> {
                StocksColumn(modifier = Modifier.weight(1f),stocksList = state.value.stocks) { nme, price, dif, type ->
                    onCardClick(nme, price, dif, type)
                }
            }
        }
    }
}

@Composable

private fun BondsColumn(modifier: Modifier = Modifier, bondsList: List<Bond>, onCardClick: (String, Double, Double, Int) -> Unit) {
    LazyColumn(
        modifier = modifier
    ) {
        items(bondsList) { bond ->
            val price = 100.0
            val priceChange = 5.0

            BondCard(
                symbol = bond.symbol,
                name = bond.name,
                country = bond.country,
                price = price,
                priceChange = priceChange,
                onCardClick = onCardClick
            )
            Spacer(Modifier.height(12.dp))
        }
    }
}

@Composable
private fun BondCard(
    symbol: String,
    name: String,
    country: String,
    price: Double,
    priceChange: Double,
    onCardClick: (String, Double, Double, Int) -> Unit
) {
    val formattedPrice = String.format("%.2f ₽", price * 105)
    CardItem(
        onClick = { onCardClick(symbol, price, priceChange, InvestmentType.Bonds.type) },
        content = {
            Row(
                Modifier.fillMaxWidth().padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(symbol, style = MaterialTheme.typography.bodyLarge.copy(fontSize = 24.sp))
                    Text(name, style = MaterialTheme.typography.bodyMedium)
                    Text(country, style = MaterialTheme.typography.bodySmall)
                    Text(formattedPrice)
                }
                Spacer(Modifier.weight(1f))
                DifText(priceChange, priceChange / price)
            }
        }
    )
}


@Composable
private fun StocksColumn(modifier: Modifier = Modifier,stocksList: Map<String, List<StockData>>, onCardClick: (String, Double, Double, Int) -> Unit, ) {
    LazyColumn(
        modifier = modifier
    ) {
        items(stocksList.keys.toList()) { symbol ->
            val stockData = stocksList[symbol]?.last()
            stockData?.let {
                val formattedPrice = it.close.toDoubleOrNull() ?: 0.0
                val priceChange = it.close.toDoubleOrNull()?.minus(it.open.toDoubleOrNull() ?: 0.0) ?: 0.0
                StockCard(
                    symbol = symbol,
                    price = formattedPrice,
                    priceChange = priceChange,
                    onCardClick = onCardClick
                )
                Spacer(Modifier.height(12.dp))
            }
        }
    }
}

@Composable
private fun StockCard(
    symbol: String,
    price: Double,
    priceChange: Double,
    onCardClick: (String, Double, Double, Int) -> Unit
) {
    val formattedPrice = String.format("%.2f ₽", price*105)
    CardItem(
        onClick = { onCardClick(symbol, price, priceChange, InvestmentType.Stocks.type) },
        content = {
            Row(
                Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(symbol, style = MaterialTheme.typography.bodyLarge.copy(fontSize = 24.sp))
                    Text(formattedPrice)
                }
                Spacer(Modifier.weight(1f))
                DifText(priceChange, priceChange / price)
            }
        }
    )
}




@Composable
fun MarketWithoutFiltersContent(
    currenciesList: List<Pair<String, Pair<Double, Double>>>,
    stocksList: Map<String, List<StockData>>,
    bondsList: List<Bond>,
    onHeaderClick: (InvestmentType) -> Unit,
    onCardClick: (String, Double, Double, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(horizontal = 12.dp, vertical = 16.dp)
    ) {
        item {
            Spacer(Modifier.height(12.dp))
            ListHeader("Валюты") { onHeaderClick(InvestmentType.Currency) }
            Spacer(Modifier.height(12.dp))
        }
        items(currenciesList) { item ->
            InvestmentCard(
                symbol = item.first.replace("RUB", ""),
                price = item.second.first,
                dif = item.second.second - item.second.first,
                onCardClick = { name, price, dif ->
                    onCardClick(name, price, dif, InvestmentType.Currency.type)
                }
            )
            Spacer(Modifier.height(12.dp))
        }
        item {
            ListHeader("Акции") { onHeaderClick(InvestmentType.Stocks) }
            Spacer(Modifier.height(12.dp))
        }
        items(stocksList.keys.toList()) { symbol ->
            val stockData = stocksList[symbol]?.last()
            stockData?.let {
                val formattedPrice = it.close.toDoubleOrNull() ?: 0.0
                val priceChange = it.close.toDoubleOrNull()?.minus(it.open.toDoubleOrNull() ?: 0.0) ?: 0.0
                StockCard(
                    symbol = symbol,
                    price = formattedPrice,
                    priceChange = priceChange,
                    onCardClick = { name, price, dif, i ->
                        onCardClick(name, price, dif, InvestmentType.Stocks.type)
                    }
                )
                Spacer(Modifier.height(12.dp))
            }
        }
        item {
            ListHeader("Облигации") { onHeaderClick(InvestmentType.Bonds) }
            Spacer(Modifier.height(12.dp))
        }
        items(bondsList) { bond ->
            val price = 100.0
            val priceChange = 5.0

            BondCard(
                symbol = bond.symbol,
                name = bond.name,
                country = bond.country,
                price = price,
                priceChange = priceChange,
                onCardClick = { symbol, price, dif, type ->
                    onCardClick(symbol, price, dif, InvestmentType.Bonds.type)
                }
            )
            Spacer(Modifier.height(12.dp))
        }
    }
}



@Composable
fun FilterItem(filter: InvestmentType?, onClick: (InvestmentType?) -> Unit){
    Box (
        modifier = Modifier
            .clickable { onClick(filter) }
            .background(color = Color.DarkGray, RoundedCornerShape(32.dp))
            .padding(horizontal = 12.dp, vertical = 2.dp)
    ){
        Text(filter?.toScreenText()?:"Все")
    }
}

@Composable
fun ListHeader(
    text: String,
    onClick: ()->Unit
){
    Row (
        Modifier.fillMaxWidth().clickable { onClick() }.padding(12.dp)
    ){
        Text(text)
        Spacer(Modifier.weight(1f))
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = null
        )
    }
}


@Composable
private fun CurrencyColumn(list: List<Pair<String, Pair<Double, Double>>>, onCardClick: (String, Double, Double) -> Unit,modifier: Modifier = Modifier,){
    LazyColumn (
        modifier = modifier
    ){
        items(list){item->
            InvestmentCard(
                symbol = item.first.replace("RUB", ""),
                price = item.second.first,
                dif = item.second.second-item.second.first,
                onCardClick
            )
            Spacer(Modifier.height(12.dp))
        }
    }
}

@Composable
fun InvestmentCard(
    symbol: String="USD",
    price: Double = 12.2,
    dif: Double = 2.0,
    onCardClick:(String, Double, Double)->Unit
){
    val formattedPrice = String.format("%.2f ₽", price)
    CardItem(
        onClick = {onCardClick(symbol, price, dif)},
        content = {
            Row(
                Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column{
                    Text(symbol, style = MaterialTheme.typography.bodyLarge.copy(fontSize = 24.sp))
                    Text(formattedPrice)
                }
                Spacer(Modifier.weight(1f))
                DifText(dif, dif/price)
            }
        }
    )

}