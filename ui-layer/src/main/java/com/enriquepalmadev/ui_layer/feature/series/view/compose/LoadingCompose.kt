package com.enriquepalmadev.ui_layer.feature.series.view.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.valentinilk.shimmer.shimmer
import com.enriquepalmadev.ui_layer.R

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun LoadingView(){
    Text(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.dark_red))
            .shimmer()
            .wrapContentHeight(),
        style = TextStyle(
            fontSize = 100.sp,
            color = Color.White,
            fontWeight = FontWeight.Black
        ),
        textAlign = TextAlign.Center,
        text = stringResource(id = R.string.m_of_marvel)
    )
}
