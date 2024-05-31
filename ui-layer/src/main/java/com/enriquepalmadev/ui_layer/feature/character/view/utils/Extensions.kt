package com.enriquepalmadev.ui_layer.feature.character.view.utils

import com.enriquepalmadev.domain_layer.feature.character.model.CharacterModel
import com.enriquepalmadev.ui_layer.R
import com.enriquepalmadev.ui_layer.feature.character.view.compose.dataclass.CharacterListModel
import com.enriquepalmadev.ui_layer.feature.character.view.compose.dataclass.CharactersUIModel
import com.enriquepalmadev.ui_layer.feature.character.view.compose.dataclass.ErrorScreenModel
import com.enriquepalmadev.ui_layer.feature.character.view.compose.dataclass.TitleListModel

fun CharactersUIModel.loadingState() = copy(loadingModel = true)


fun CharactersUIModel.errorState() =
    copy(
        loadingModel = false,
        errorScreenModel = ErrorScreenModel(
            image = R.drawable.deadpool_no_connection,
            message = R.string.unknownError.toString()
        )
    )


fun CharactersUIModel.errorEither() =
    copy(
        loadingModel = false,
        errorScreenModel = ErrorScreenModel(
            image = R.drawable.deadpool_no_connection,
            message = R.string.unknownError.toString()
        )
    )


fun CharactersUIModel.successEither(list: List<CharacterModel>?) =
    copy(
        loadingModel = false,
        characterListModel = CharacterListModel(
            titleListModel = TitleListModel(
                icon = R.drawable.ironman,
                title = R.string.characters_view.toString()
            ),
        characterList = list
        )
    )
