package com.enriquepalmadev.ui_layer.feature.series.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ErrorView(error: String, msg: String, drawable: Int){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Box(
            modifier = Modifier
                .size(300.dp)
                .align(Alignment.CenterHorizontally)
                .absolutePadding(top = 100.dp, bottom = 5.dp, left = 5.dp, right = 5.dp)
        ){
            Image(
                modifier = Modifier
                    .fillMaxSize(),
                painter = painterResource(id = drawable),
                contentDescription = "error")
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            text = error,
            style = TextStyle(
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontWeight = FontWeight.Black
            )
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            text = msg,
            style = TextStyle(
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                color = Color.Black
            )
        )
    }
}
