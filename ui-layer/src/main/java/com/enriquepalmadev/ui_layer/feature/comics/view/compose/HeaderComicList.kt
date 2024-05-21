package com.enriquepalmadev.ui_layer.feature.comics.view.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.enriquepalmadev.ui_layer.R
import com.enriquepalmadev.ui_layer.feature.comics.view.model.ComicListScreenHeader
import com.enriquepalmadev.ui_layer.feature.comics.view.model.SearchBarHeader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeaderComicList(
    comicListScreenHeader: ComicListScreenHeader,
    onBackButtonClicked: () -> Unit,
    onSearchBar: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        comicListScreenHeader.let { headerModel ->
            CenterAlignedTopAppBar(
                modifier = Modifier
                    .fillMaxWidth(),
                title = {
                    Image(
                        modifier = Modifier
                            .size(128.dp, 60.dp)
                            .align(Alignment.CenterHorizontally),
                        painter = painterResource(headerModel.imageLogo),
                        contentDescription = null,
                    )
                },
                navigationIcon = {
                    IconButton(
                        modifier = Modifier
                            .padding(start = 20.dp),
                        onClick = { onBackButtonClicked() }
                    ) {
                        Icon(
                            imageVector = headerModel.icon,
                            contentDescription = "",
                            tint = Color.Red
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(Color.Transparent)
            )

            Spacer(modifier = Modifier.height(16.dp))

            SearchComic(
                searchBarHeader = SearchBarHeader(placeholder = headerModel.searchBarHeader.placeholder),
                onSearchBar = { text ->
                    onSearchBar(text)
                }
            )
        }
    }
}

@Composable
fun SearchComic(
    searchBarHeader: SearchBarHeader,
    onSearchBar: (String) -> Unit
) {
    val textState = remember {
        mutableStateOf(TextFieldValue(""))
    }

    TextField(
        value = textState.value,
        onValueChange = { value ->
            textState.value = value
            onSearchBar(value.text)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .clip(RoundedCornerShape(30.dp))
            .border(2.dp, Color.DarkGray, RoundedCornerShape(30.dp)),
        placeholder = {
            Text(stringResource(id = searchBarHeader.placeholder))
        },
        colors = TextFieldDefaults.colors(
            disabledPlaceholderColor = Color.White
        ),
        maxLines = 1,
        singleLine = true,
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = 20.sp
        )
    )
}

@Preview(showBackground = true)
@Composable
fun HeaderComicListPreview() {
    HeaderComicList(comicListScreenHeader = ComicListScreenHeader(
        imageLogo = R.drawable.marvel_comics_logo,
        icon = Icons.AutoMirrored.Filled.ArrowBack,
        placeholderText = R.string.search_text,
        searchBarHeader = SearchBarHeader(placeholder = R.string.search_text)
    ),
        onBackButtonClicked = {},
        {}
    )
}