package com.enriquepalmadev.ui_layer.feature.series.view.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.enriquepalmadev.domain_layer.feature.series.models.FilmSerieModel
import com.enriquepalmadev.ui_layer.R
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun Series(
    series: List<FilmSerieModel>,
    dialogOrderBy: () -> Unit,
    itemClicked: (id: Int) -> Unit,
    favClicked: () -> Unit,
    onSearchQueryChange : (newText : String) -> Unit
) {
    Column {
        SeriesListHeader(dialogOrderBy, onSearchQueryChange)
        SeriesListBody(series, itemClicked, favClicked)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeriesListHeader(dialogOrderBy: () -> Unit, onSearchQueryChange: (newText: String) -> Unit) {
    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    Row {
        Box (
            modifier = Modifier
                .width(230.dp)
                .height(80.dp)
                .padding(5.dp, 8.dp, 5.dp, 5.dp),
            contentAlignment = Alignment.CenterStart
        ){
            SearchBar(
                query = text,
                onQueryChange = {
                    text = it
                    onSearchQueryChange(text)
                                },
                onSearch = { active = false},
                active = active,
                onActiveChange = { active = it },
                placeholder = {
                    Text(text = stringResource(id = R.string.search))
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null)
                },
                trailingIcon = {
                    if (active){
                        Icon(
                            modifier = Modifier.clickable {
                                if (text.isNotEmpty()){
                                    text = ""
                                } else {
                                    active = false
                                    onSearchQueryChange(text)
                                }
                            },
                            imageVector = Icons.Default.Clear,
                            contentDescription = null
                        )
                    }
                }
            ) { }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(5.dp, 12.dp, 5.dp, 5.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Button(
                modifier = Modifier.height(40.dp),
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
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun SeriesListBody(series: List<FilmSerieModel>, itemClicked: (id: Int) -> Unit, favClicked: () -> Unit) {

    // var favIcon: Int
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        content = {
            items(series.size) { index ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp, 8.dp, 5.dp, 5.dp)
                        .clickable { itemClicked(series[index].id) }
                        .background(Color.White)
                ) {
                    Column {
                        Box {
                            GlideImage(
                                model = "${series[index].thumbnailPath}.${series[index].thumbnailExt}",
                                contentDescription = null,
                                loading = placeholder(R.drawable.loading),
                                failure = placeholder(R.drawable.error_404),
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier
                                    .height(200.dp)
                                    .width(200.dp)
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(end = 3.dp, top = 3.dp),
                                contentAlignment = Alignment.CenterEnd
                            ){
                                /*
                                favIcon = if (series[index].fav){
                                    R.drawable.ic_full_favorite_24dp
                                } else {
                                    R.drawable.ic_border_favorite_24dp
                                }
                                 */
                                Image(
                                    painter = painterResource(
                                        id = R.drawable.ic_border_favorite_24dp
                                    ),
                                    contentDescription = null,
                                    alignment = Alignment.CenterEnd,
                                    modifier = Modifier.clickable { favClicked() }
                                )
                            }
                        }
                        // Title
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(colorResource(id = R.color.light_red))
                                .height(50.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                modifier = Modifier.padding(5.dp, 0.dp, 5.dp, 5.dp),
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

private val film : FilmSerieModel = FilmSerieModel(
    id = 1,
    title = "Title",
    description = "Description",
    startYear = 0,
    thumbnailExt = "",
    thumbnailPath = ""
)

private val seriesMock: List<FilmSerieModel> = listOf(film, film, film, film, film)

@Preview (showSystemUi = true)
@Composable
fun SeriesView(){
    Series(series = seriesMock, dialogOrderBy = {}, itemClicked = {}, favClicked = {}) {
    }
}

@Preview
@Composable
fun Header(){
    SeriesListHeader(dialogOrderBy = {}) {
    }
}

@Preview
@Composable
fun Body(){
    SeriesListBody(seriesMock, {}) {
    }
}