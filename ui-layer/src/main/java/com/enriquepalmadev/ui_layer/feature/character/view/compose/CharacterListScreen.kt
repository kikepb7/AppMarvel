package com.enriquepalmadev.ui_layer.feature.character.view.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.enriquepalmadev.domain_layer.feature.character.model.CharacterModel
import com.enriquepalmadev.ui_layer.R
import com.enriquepalmadev.ui_layer.feature.character.view.CharactersFragmentDirections
import com.enriquepalmadev.ui_layer.feature.character.viewmodel.CharactersViewModel

@Composable
fun CharacterListScreen(
    modifier: Modifier,
    navController: NavController,
    state: CharactersViewModel.State,
    characterList: List<CharacterModel>?
){
    //val state by charactersViewModel.state.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        //Encabezado
        HeaderCharacterList(modifier = modifier, navController = navController)
        CharacterList(characters = characterList, navController = navController)
    }
}

@Composable
fun HeaderCharacterList(modifier: Modifier, navController: NavController) {

}

@Composable
fun CharacterList(characters: List<CharacterModel>?, navController: NavController){
    LazyColumn {
        items(characters ?: emptyList()) { character ->
            CharacterItem(character, navController)
        }
    }
}

@Composable
fun CharacterItem(character: CharacterModel, navController: NavController) {
    Row(
        modifier = Modifier
            .clickable {
                navController.navigate(
                    CharactersFragmentDirections.actionCharactersFragmentToItemDetailsCharactersFragment(
                        character.id
                    )
                )
            }
            .padding(16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        //Cargamos la imagen
        CharacterImage(imageUrl = character.thumbnailDTO, modifier = Modifier.size(64.dp))
        //Cargamos el texto
        Text(text = character.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(modifier = Modifier.width(8.dp))
    }
}

@Composable
fun CharacterImage(imageUrl: String?, modifier: Modifier){
    // Verifica si la URL de la imagen no es nula o vacía
    if (!imageUrl.isNullOrBlank()) {
        Image(
            painter = painterResource(id = R.drawable.deadpool_no_connection),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
        )
    } else {
        // Si la URL es nula o vacía, muestra una imagen de marcador de posición o un mensaje alternativo
        Text(text = "No Image", modifier = modifier)
    }
}


/* funcion Errores */
@Composable
fun showError(errorMessageId: Int) {
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
fun showLoader() {
    // Muestra el ProgressBar
    CircularProgressIndicator(modifier = Modifier)
}