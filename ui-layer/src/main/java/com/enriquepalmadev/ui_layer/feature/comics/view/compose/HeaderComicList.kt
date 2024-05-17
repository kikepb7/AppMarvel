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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.enriquepalmadev.ui_layer.R
import com.enriquepalmadev.ui_layer.feature.comics.view.model.ComicListScreenHeader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeaderComicList(
    comicListScreenHeader: ComicListScreenHeader,
    onBackButtonClicked: () -> Unit
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
                placeholder = comicListScreenHeader.placeholderText,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}

@Composable
fun SearchComic(
    placeholder: String,
    modifier: Modifier
) {

    val textState = remember {
        mutableStateOf(TextFieldValue(""))
    }

    TextField(
        value = textState.value,
        onValueChange = { value ->
            textState.value = value
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp)
            .clip(RoundedCornerShape(30.dp))
            .border(2.dp, Color.DarkGray, RoundedCornerShape(30.dp)),
        placeholder = {
            Text(text = placeholder)
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
        placeholderText = "Buscar ...",
        onClickSearch = {}
    ),
        onBackButtonClicked = {}
    )
}