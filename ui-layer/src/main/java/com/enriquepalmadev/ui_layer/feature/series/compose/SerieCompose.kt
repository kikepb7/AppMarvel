package com.enriquepalmadev.ui_layer.feature.series.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.enriquepalmadev.domain_layer.feature.series.models.FilmSerieModel
import com.enriquepalmadev.ui_layer.R

// @Preview(showSystemUi = true)
@Composable
fun Series(series: List<FilmSerieModel>, dialogOrderBy: () -> Unit){
    Column {
        ButtonOrderBy(dialogOrderBy)
        SeriesList(series)
    }
}

@Composable
fun ButtonOrderBy(dialogOrderBy: () -> Unit) {
    Box (
        modifier = Modifier
            .fillMaxWidth()
            .absolutePadding(5.dp, 5.dp, 5.dp, 0.dp),
        contentAlignment = Alignment.CenterEnd){
        Button(
            onClick = { dialogOrderBy() },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.dark_red)
            )
        ) {
            Text(
                text = stringResource(R.string.orderby),
                color = Color.White
            )
        }
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun SeriesList(series: List<FilmSerieModel>) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            content = {
                items(series.size) { index ->
                        Card(
                            backgroundColor = Color.White,
                            modifier = Modifier
                                .fillMaxWidth()
                                .absolutePadding(5.dp, 5.dp, 5.dp, 5.dp)
                        ) {
                            Column (
                            ) {
                                GlideImage(
                                    model = "${series[index].thumbnailPath}.${series[index].thumbnailExt}",
                                    contentDescription = "${series[index].thumbnailPath}.${series[index].thumbnailExt}",
                                    loading = placeholder(R.drawable.loading),
                                    failure = placeholder(R.drawable.error_404),
                                    contentScale = ContentScale.FillBounds,
                                    modifier = Modifier
                                        .height(200.dp)
                                        .width(200.dp)
                                )
                                // Title
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(colorResource(id = R.color.light_red))
                                        .height(50.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        modifier = Modifier.absolutePadding(5.dp, 0.dp, 5.dp, 5.dp),
                                        text = series[index].title,
                                        fontSize = 14.sp,
                                        color = Color.White
                                    )
                                }

                            }

                        }

                    }

            }
        )
}


/*
@Preview
@Composable
fun Test(){
    SeriesList(series = list)
}
val film: FilmSerieModel = FilmSerieModel(1, "Title", "desc", "", "", 1)
val film2: FilmSerieModel = FilmSerieModel(2, "Title2", "desc", "", "", 2)
val list  = listOf<FilmSerieModel>(film, film2)

 */
