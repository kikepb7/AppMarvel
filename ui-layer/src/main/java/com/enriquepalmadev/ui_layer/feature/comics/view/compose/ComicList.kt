package com.enriquepalmadev.ui_layer.feature.comics.view.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.enriquepalmadev.domain_layer.feature.comics.model.ComicModel
import com.enriquepalmadev.ui_layer.R
import com.enriquepalmadev.ui_layer.feature.comics.view.model.ComicListScreenItemModel
import com.enriquepalmadev.ui_layer.feature.comics.view.model.ComicListScreenTitle

@Composable
fun ComicList(
    comicListScreenTitle: ComicListScreenTitle?,
    comicListScreenItemModel: List<ComicListScreenItemModel>?,
    onComicClicked: (ComicModel) -> Unit
) {
    Column(
        modifier = Modifier
    ) {
        comicListScreenTitle?.let {
            TitleList(comicListScreenTitle = comicListScreenTitle)
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyRow {
            comicListScreenItemModel?.let {
                items(it) { comicItem ->
                    ComicItem(comicListScreenItemModel = comicItem) { comic ->
                        onComicClicked(comic)
                    }
                }
            }
        }
    }
}

@Composable
fun TitleList(
    comicListScreenTitle: ComicListScreenTitle
) {
    Row(
        modifier = Modifier
            .padding(start = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = comicListScreenTitle.icon),
            contentDescription = "Comic List Icon",
            modifier = Modifier
                .size(30.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = stringResource(id = comicListScreenTitle.title),
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontSize = 22.sp
        )
    }
}

@Composable
fun ComicItem(
    comicListScreenItemModel: ComicListScreenItemModel,
    onComicClicked: (ComicModel) -> Unit
) {
    Card(
        modifier = Modifier
            .size(128.dp, 170.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        onClick = {
            onComicClicked(comicListScreenItemModel.comic)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(comicListScreenItemModel.comic.thumbnail)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentDescription = "Comic Item"
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = comicListScreenItemModel.comic.title,
                fontSize = 12.sp,
                style = MaterialTheme.typography.displaySmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}

@Preview
@Composable
fun ComicListPreview() {
    ComicList(
        comicListScreenTitle =
        ComicListScreenTitle(
            icon = R.drawable.ironman,
            title = R.string.all_comics_title
        ),
        comicListScreenItemModel = listOf(
            ComicListScreenItemModel(
                comic = ComicModel(
                    id = 1,
                    title = "Spiderman",
                    description = "Spiderman",
                    pageCount = 30,
                    thumbnail = "http://i.annihil.us/u/prod/marvel/i/mg/9/b0/62f3c7bba6677.jpg"
                )
            ),
            ComicListScreenItemModel(
                comic = ComicModel(
                    id = 1,
                    title = "Spiderman",
                    description = "Spiderman",
                    pageCount = 30,
                    thumbnail = "http://i.annihil.us/u/prod/marvel/i/mg/9/b0/62f3c7bba6677.jpg"
                )
            )
        ), onComicClicked = {}
    )
}

@Preview
@Composable
fun TitleListPreview() {
    TitleList(
        comicListScreenTitle = ComicListScreenTitle(
            icon = R.drawable.ironman,
            title = R.string.favorite_comics_title
        )
    )
}

@Preview(showBackground = true)
@Composable
fun ComicItemPreview() {
    ComicItem(
        comicListScreenItemModel =
        ComicListScreenItemModel(
            comic = ComicModel(
                id = 1,
                title = "Spiderman",
                description = "Spiderman",
                pageCount = 30,
                thumbnail = "http://i.annihil.us/u/prod/marvel/i/mg/9/b0/62f3c7bba6677.jpg"
            )
        ),
        onComicClicked = {}
    )
}