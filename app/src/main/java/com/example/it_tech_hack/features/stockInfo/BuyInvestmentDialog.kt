package com.example.it_tech_hack.features.stockInfo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.it_tech_hack.features.common.CardItem
import com.example.it_tech_hack.features.mainScreen.DifText

@Composable
fun BuyInvestmentDialog(
    name: String,
    price: Double,
    dif: Double,
    type: Int,
    onBuyClicked:(String, Int)->Unit
) {
    CardItem(
        onClick = {},
        content = {
            Column (
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(name, style = MaterialTheme.typography.bodyLarge.copy(fontSize = 22.sp))
                Spacer(Modifier.height(12.dp))
                Text("$price ₽")
                Spacer(Modifier.height(12.dp))
                DifText(dif, dif/price)
                Spacer(Modifier.height(12.dp))
                Button(modifier = Modifier.fillMaxWidth(),
                    onClick = {onBuyClicked(name, type)},
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.DarkGray
                    )) {
                    Text("Buy")
                }

            }
        }

    )

}
