package com.enriquepalmadev.ui_layer.feature.comics.view.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.enriquepalmadev.ui_layer.R
import com.enriquepalmadev.ui_layer.feature.comics.view.model.ComicListScreenError

@Composable
fun ErrorScreen(
    comicListScreenError: ComicListScreenError
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = comicListScreenError.image),
            contentDescription = "Image Error"
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = comicListScreenError.errorMsg,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Red,
            textAlign = TextAlign.Center
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    ErrorScreen(
        comicListScreenError = ComicListScreenError(
            image = R.drawable.unauthorized_error_logo,
            errorMsg = "Error 401"
        )
    )
}