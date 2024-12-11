package com.example.it_tech_hack.features.briefcase

import com.example.it_tech_hack.domain.useCases.GetBriefcaseCostUseCase
import org.koin.androidx.compose.koinViewModel

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.it_tech_hack.domain.models.InvestmentType
import com.example.it_tech_hack.domain.models.toScreenText
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun PieChart(
    data: List<Pair<InvestmentType, Double>>,
    modifier: Modifier = Modifier
) {
    val colors = listOf(
        Color(0xFF690303),
        Color(0xFF990303),
        Color(0xFF90156),
        Color(0xFFFF5722)
    )

    Canvas(modifier = modifier.size(300.dp).aspectRatio(1f)) {
        val total = data.sumOf { it.second }
        var startAngle = 0f

        data.forEachIndexed { index, (investmentType, percentage) ->
            val sweepAngle = (percentage / total * 360).toFloat()
            drawArc(
                color = colors[index % colors.size],
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = true,
                size = Size(size.width, size.height)
            )
            val angle = startAngle + sweepAngle / 2
            val radius = size.minDimension / 3
            val x = size.center.x + radius * cos(angle * PI / 180).toFloat()
            val y = size.center.y + radius * sin(angle * PI / 180).toFloat()

            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    "${"%.1f".format(percentage)}%",
                    x,
                    y,
                    android.graphics.Paint().apply {
                        color = android.graphics.Color.BLACK
                        textAlign = android.graphics.Paint.Align.CENTER
                        textSize = 40f
                    }
                )
            }

            startAngle += sweepAngle
        }
    }
}

@Composable
fun BriefcaseScreen(
    viewModel: BriefcaseViewModel = koinViewModel(),
    onCardClick: (String, Double) -> Unit
) {
    val state = viewModel.screenState.collectAsState()

    if (state.value.percentageData.isNotEmpty() && state.value.priceData.isNotEmpty()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Box(){
                PieChart(
                    data = state.value.percentageData,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                        .padding(bottom = 16.dp)
                )
            }

            LazyColumn() {
                items(state.value.priceData) { (symbol, totalPrice) ->
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .clickable { onCardClick(symbol, totalPrice) },
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF424242)) // Dark gray color
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Актив: $symbol",
                                color = Color.White,
                                fontSize = 16.sp
                            )
                            Text(
                                text = "Общая стоимость: \$${"%.2f".format(totalPrice)}",
                                color = Color.White,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }
    } else {
        Text("Нет данных", Modifier.padding(16.dp), fontSize = 18.sp, textAlign = TextAlign.Center)
    }
}




