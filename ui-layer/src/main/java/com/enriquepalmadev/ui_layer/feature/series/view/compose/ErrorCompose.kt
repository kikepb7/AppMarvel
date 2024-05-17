package com.enriquepalmadev.ui_layer.feature.series.view.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.enriquepalmadev.ui_layer.R

@Composable
fun ErrorView(error: String, msg: String, drawable: Int){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .fillMaxWidth(1f)
                .padding(top = 70.dp, bottom = 5.dp, start = 5.dp, end = 5.dp),
            painter = painterResource(id = drawable),
            contentDescription = "error"
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp, bottom = 5.dp, start = 5.dp, end = 5.dp),
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

@Preview(showSystemUi = true)
@Composable
fun Error (){
    ErrorView(error = "Title", msg = "Description", drawable = R.drawable.captain_empty)
}
