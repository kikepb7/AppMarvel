package com.enriquepalmadev.ui_layer.feature.character.view.compose

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.enriquepalmadev.domain_layer.feature.character.model.CharacterModel
import com.enriquepalmadev.ui_layer.R
import com.enriquepalmadev.ui_layer.feature.character.view.compose.dataclass.CharactersUIModel
import com.enriquepalmadev.ui_layer.feature.character.view.compose.screens.HeaderCharacterList

@Composable
fun CharacterListScreen(
    model: CharactersUIModel,
    onCharacterClicked: (CharacterModel) -> Unit,
    dialogOrderBy: () -> Unit,
    onSearchQueryChange : (newText : String) -> Unit,
    onFavClicked: () -> Unit
){
    val titleListModel = model.characterListModel?.titleListModel
    val charactersList = model.characterListModel?.characterList

    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
    ){
        if(model.loadingModel){
            LoadingScreen()
        }

        model.errorScreenModel?.let {
            ErrorScreen(
                errorMessageImage = R.drawable.deadpool_no_connection,
                errorMessageString = R.string.unknownError
            )
        }


        HeaderCharacterList(titleListModel = titleListModel, dialogOrderBy = dialogOrderBy, onSearchQueryChange = onSearchQueryChange )

        Spacer(modifier = Modifier.height(16.dp))

        CharacterList(
            list = charactersList,
            onCharacterClicked = onCharacterClicked,
            onFavClicked = onFavClicked
        )
    }
}


@Composable
fun CharacterList(list: List<CharacterModel>?, onCharacterClicked: (CharacterModel) -> Unit, onFavClicked: () -> Unit){
    if (list.isNullOrEmpty()) {
        val emptyListDescription = stringResource(id = R.string.emptyList)
        Text(
            emptyListDescription,
            Modifier.semantics { contentDescription = emptyListDescription }
        )
    } else {
        LazyColumn {
            items(list) { character ->
                CharacterItem(character = character, onCharacterClicked = onCharacterClicked, onFavClicked = onFavClicked)
            }
        }
    }
}

@Composable
fun CharacterItem(character: CharacterModel, onCharacterClicked: (CharacterModel) -> Unit, onFavClicked: () -> Unit) {
    val characterImageDescription =  stringResource(id = R.string.characterImageDescription) + character.name
    val characterNameDescription = stringResource(id = R.string.characterNameDescription) + character.name

    Column(
        modifier = Modifier
            .clickable { onCharacterClicked(character) }
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CharacterImage(
            imageUrl = character.thumbnailDTO,
            isFavorite = false,
            onFavClicked = onFavClicked,
            modifier = Modifier
                .size(150.dp)
                .semantics { contentDescription = characterImageDescription }
        )
        Text(
            modifier = Modifier
                .padding(top = 8.dp)
                .semantics { contentDescription = characterNameDescription },
            text = character.name,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color.White
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CharacterImage(imageUrl: String?, isFavorite: Boolean, onFavClicked: () -> Unit, modifier: Modifier = Modifier){
    val currentImage = remember { mutableStateOf(R.drawable.ic_border_favorite_24dp) }

    Column(modifier = modifier) {
        Box(modifier = Modifier
            .height(200.dp)
            .width(200.dp)) {
            if (!imageUrl.isNullOrBlank()) {
                GlideImage(
                    model = imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxSize()
                        .height(200.dp)
                        .width(200.dp)
                )
            } else {
                GlideImage(
                    model = R.drawable.captain_empty,
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxSize()
                        .height(200.dp)
                        .width(200.dp)
                )
            }
            Image(
                painter = painterResource( id = currentImage.value ),
                contentDescription = "like",
                alignment = Alignment.TopEnd,
                modifier = Modifier
                    .clickable {
                        currentImage.value =
                            if (currentImage.value == R.drawable.ic_border_favorite_24dp) {
                                R.drawable.ic_full_favorite_24dp
                            } else {
                                R.drawable.ic_border_favorite_24dp
                            }
                        onFavClicked()
                    }
                    .fillMaxWidth()
                    .padding(end = 3.dp, top = 3.dp),
            )
        }

    }
}

@Composable
fun ErrorScreen(@DrawableRes errorMessageImage: Int, @StringRes errorMessageString: Int) {
    if (errorMessageImage != 0) {
        val errorImageDescription = stringResource(id = R.string.errorImageDescription)
        val errorMessageDescription = stringResource(id = errorMessageString)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.deadpool_no_connection),
                contentDescription = null,
                modifier = Modifier
                    .size(150.dp)
                    .padding(bottom = 16.dp)
                    .semantics { contentDescription = errorImageDescription }
            )
            Text(
                text = stringResource(id = errorMessageString),
                color = Color.Red,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(8.dp)
                    .semantics { contentDescription = errorMessageDescription }
            )
        }
    }
}

/* funcion Loader */
@Composable
fun LoadingScreen() {
    val loadingMessage = stringResource(id = R.string.loadingMessage)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .semantics { contentDescription = loadingMessage },
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}


@Preview
@Composable
fun PreviewCharacterList(){
    val model = CharactersUIModel()

    CharacterList(list = model.characterListModel?.characterList, onCharacterClicked = {}, onFavClicked = {} )
}

@Preview
@Composable
fun PreviewCharacterItem(){
    val character = CharacterModel(
        id = 1,
        name = "Spider-Man",
        thumbnailDTO = "https://example.com/spiderman.jpg",
        description = "Friendly neighborhood Spider-Man"
    )
    CharacterItem(
        character = character,
        onCharacterClicked = {},
        onFavClicked = {}
    )
}

@Preview
@Composable
fun PreviewCharacterImage() {
    CharacterImage(
        imageUrl = "https://example.com/spiderman.jpg",
        isFavorite = false,
        onFavClicked = {},
        modifier = Modifier.size(200.dp)
    )
}

@Preview
@Composable
fun PreviewErrorScreen() {
    ErrorScreen(
        errorMessageImage = R.drawable.deadpool_no_connection,
        errorMessageString = R.string.no_connection
    )
}

@Preview
@Composable
fun PreviewLoadingScreen() {
    LoadingScreen()
}