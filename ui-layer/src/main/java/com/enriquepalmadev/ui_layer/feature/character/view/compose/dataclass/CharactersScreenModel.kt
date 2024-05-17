package com.enriquepalmadev.ui_layer.feature.character.view.compose.dataclass

import androidx.compose.ui.graphics.vector.ImageVector
import com.enriquepalmadev.domain_layer.feature.character.model.CharacterModel

data class CharactersScreenModel(
    val loadingModel: Boolean = false,
    val errorScreenModel: ErrorScreenModel? = null,
    val characterListModel: CharacterListModel? = null,
    val headerCharacterListModel: HeaderCharacterListModel? = null
)

data class ErrorScreenModel(
    val image: Int,
    val message: String
)

data class TitleListModel(
    val icon: Int,
    val title: String
)

data class CharacterItemModel(
    val character: CharacterModel
)

data class HeaderCharacterListModel(
    val imageLogo: Int,
    val icon: ImageVector,
    val placeholder: String,
    val onClickSearch: () -> Unit
)

data class CharacterListModel(
    val titleListModel: TitleListModel,
    val characterList: List<CharacterModel>?
)

/*data class Loading(
    val loader: Boolean = false
)
 */


