package com.enriquepalmadev.ui_layer.feature.comics.view.compose

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.enriquepalmadev.ui_layer.R
import kotlinx.coroutines.delay

@Composable
fun LoadingScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LoadingAnimation()
    }
}

@Composable
fun LoadingAnimation() {
    val images = listOf(
        R.drawable.spiderman_loading,
        R.drawable.ironman_loading,
        R.drawable.deadpool_loading
    )

    val circles = remember {
        List(images.size) { Animatable(initialValue = 0f) }
    }

    circles.forEachIndexed { index, animatable ->
        LaunchedEffect(key1 = animatable) {
            delay(index * 200L)
            animatable.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = 1200
                        0.0f at 0 using LinearOutSlowInEasing
                        1.0f at 300 using LinearOutSlowInEasing
                        0.0f at 600 using LinearOutSlowInEasing
                        0.0f at 1200 using LinearOutSlowInEasing
                    },
                    repeatMode = RepeatMode.Restart
                )
            )
        }
    }

    val circleValues = circles.map { it.value }
    val distance = with(LocalDensity.current) { 20.dp.toPx() }

    Row {
        circleValues.forEachIndexed { index, value ->
            Image(
                painter = painterResource(id = images[index % images.size]),
                contentDescription = "Loading images",
                modifier = Modifier
                    .size(45.dp)
                    .graphicsLayer {
                        translationY = -value * distance
                    }
            )
            if (index != circles.lastIndex) {
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    LoadingScreen()
}