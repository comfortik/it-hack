package com.example.it_tech_hack.features.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.it_tech_hack.R
import com.example.it_tech_hack.ui.theme.IttechhackTheme


@Composable
fun CardItem(
    content: @Composable ()->Unit,
    onClick: ()->Unit = {}
) {
    Column(
        Modifier.fillMaxWidth()
            .clickable { onClick() }
            .background(
                colorResource(R.color.light_gray),
                RoundedCornerShape(12.dp))


    ){
        content()
    }
}

