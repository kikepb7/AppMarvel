package com.enriquepalmadev.ui_layer.feature.character.view.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.enriquepalmadev.domain_layer.feature.character.model.CharacterModel
import com.enriquepalmadev.ui_layer.R
import com.enriquepalmadev.ui_layer.feature.character.view.compose.dataclass.CharactersScreenModel
import com.enriquepalmadev.ui_layer.feature.character.view.compose.dataclass.TitleListModel
import com.enriquepalmadev.ui_layer.feature.character.view.compose.screens.HeaderCharacterList

@Composable
fun CharacterListScreen(
    model: CharactersScreenModel,
    onCharacterClicked: (CharacterModel) -> Unit,
    dialogOrderBy: () -> Unit,
    onSearchQueryChange : (newText : String) -> Unit
){
    //val state by charactersViewModel.state.collectAsState()
    val titleListModel = model.characterListModel?.titleListModel
    val charactersModel = model.characterListModel?.characterList

    
    Column(
        modifier = Modifier.fillMaxSize().background(Color.Black),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        if(model.loadingModel){
            LoadingScreen()
        }

        model.errorScreenModel?.let {
            ErrorScreen(
                errorMessageId = R.drawable.deadpool_no_connection,
                errorMessageString = R.string.unknownError.toString()
            )
        }


        HeaderCharacterList(dialogOrderBy = dialogOrderBy, onSearchQueryChange = onSearchQueryChange )

        Spacer(modifier = Modifier.height(16.dp))

        CharacterList(
            titleListModel = titleListModel,
            list = charactersModel,
            onCharacterClicked = onCharacterClicked
        )
    }
}


@Composable
fun CharacterList(titleListModel: TitleListModel?, list: List<CharacterModel>?, onCharacterClicked: (CharacterModel) -> Unit){
//IF si la lista esta vacia  ver empty view
    list?.let {characterList ->
        LazyColumn {
            items(characterList) { character ->
                CharacterItem(character = character, onCharacterClicked = onCharacterClicked)
            }
        }
    }
}

//TODO Pasar la imagen
@Composable
fun CharacterItem(character: CharacterModel, onCharacterClicked: (CharacterModel) -> Unit) {
    Row(
        modifier = Modifier
            .clickable {
                onCharacterClicked(character)
            }
            .padding(16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box (//TODO Verificar si hace falta nox
            modifier = Modifier
        ){
            //Cargamos la imagen
            CharacterImage(imageUrl = character.thumbnailDTO, isFavorite = false, onFavoriteClicked = {}, title = "Titulo", modifier = Modifier.size(64.dp))
        }
        Spacer(modifier = Modifier.width(8.dp))
        //Cargamos el texto
        Text(
            text = character.name,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color.White
        )
        //Description
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CharacterImage(imageUrl: String?, isFavorite: Boolean, onFavoriteClicked: () -> Unit, title: String, modifier: Modifier = Modifier){
    Column(modifier = modifier) {
        Box(modifier = Modifier.height(200.dp).width(200.dp)) {
            if (!imageUrl.isNullOrBlank()) {
                GlideImage(
                    model = imageUrl,
                    contentDescription = null,
                    //loading = { Box(Modifier.fillMaxSize()) { CircularProgressIndicator() } },
                    //failure = { Box(Modifier.fillMaxSize()) { Text(text = "Error") } },
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(text = "No Image", modifier = Modifier.align(Alignment.Center))
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 3.dp, top = 3.dp),
                contentAlignment = Alignment.TopEnd
            ) {
                Image(
                    painter = painterResource(id = if (isFavorite) R.drawable.ic_full_favorite_24dp else R.drawable.ic_border_favorite_24dp),
                    contentDescription = "like",
                    alignment = Alignment.TopEnd,
                    //modifier = Modifier.clickable(onClick = onFavoriteClicked)
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.LightGray)
                .height(50.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.padding(5.dp),
                text = title,
                fontSize = 14.sp,
                color = Color.Black
            )
        }
    }
}


/* funcion Errores */
@Composable
fun ErrorScreen(errorMessageId: Int, errorMessageString: String) {
    // Muestra el mensaje de error solo si errorMessageId no es 0
    if (errorMessageId != 0) {
        Text(
            text = stringResource(id = errorMessageId),
            color = Color.Red,
            modifier = Modifier.padding(8.dp)
        )
    }
}

/* funcion Loader */
@Composable
fun LoadingScreen() {
    // Muestra el ProgressBar
    CircularProgressIndicator(modifier = Modifier)
}


@Preview
@Composable
fun PreviewCharacterList(){
    val model: CharactersScreenModel = CharactersScreenModel()

    CharacterList(titleListModel = model.characterListModel?.titleListModel, list = model.characterListModel?.characterList, onCharacterClicked = {} )
}