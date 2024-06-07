package com.enriquepalmadev.ui_layer.feature.character.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enriquepalmadev.domain_layer.commons.Either
import com.enriquepalmadev.domain_layer.feature.character.model.CharacterModel
import com.enriquepalmadev.domain_layer.feature.character.useCase.FiltListGetCharacterUseCase
import com.enriquepalmadev.domain_layer.feature.character.useCase.GetCharacterUseCase
import com.enriquepalmadev.domain_layer.feature.character.useCase.ListOrderFavouritesGetCharacterUseCase
import com.enriquepalmadev.domain_layer.feature.character.useCase.ListOrderNameGetCharacterUseCase
import com.enriquepalmadev.ui_layer.R
import com.enriquepalmadev.ui_layer.feature.character.view.compose.dataclass.CharacterListModel
import com.enriquepalmadev.ui_layer.feature.character.view.compose.dataclass.CharactersUIModel
import com.enriquepalmadev.ui_layer.feature.character.view.compose.dataclass.ErrorScreenModel
import com.enriquepalmadev.ui_layer.feature.character.view.compose.dataclass.TitleListModel
import com.enriquepalmadev.ui_layer.feature.character.view.utils.errorEither
import com.enriquepalmadev.ui_layer.feature.character.view.utils.errorState
import com.enriquepalmadev.ui_layer.feature.character.view.utils.loadingState
import com.enriquepalmadev.ui_layer.feature.character.view.utils.successEither
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val getCharacterUseCase : GetCharacterUseCase,
    private val filtListGetCharacterUseCase : FiltListGetCharacterUseCase,
    private val listOrderNameGetCharacterUseCase : ListOrderNameGetCharacterUseCase,
    private val listOrderNameFavouritesGetCharacterUseCase : ListOrderFavouritesGetCharacterUseCase
): ViewModel() {
    private val _state = MutableStateFlow<CharactersUIModel>(CharactersUIModel())
    val state = _state.asStateFlow()


    fun getCharacterList(){
        viewModelScope.launch {
            //Para probar lo de los Errores
            getCharacterUseCase.getCharacterList()
                .onStart {
                    _state.update {
                        it.loadingState()
                    }
                }
                .catch {e ->
                    _state.update {
                        it.errorState()
                    }
                }
                .collect{characterList ->
                    when(characterList){
                        is Either.Error -> _state.update {
                            it.errorEither()
                        }
                        is Either.Success -> _state.update {
                            it.successEither(characterList.data)
                        }
                    }
                }
        }
    }

    fun getCharacterFiltList(filt: String){
        viewModelScope.launch {
            filtListGetCharacterUseCase.getCharacterFilterList(filt)
                .onStart {
                    _state.update {
                        it.loadingState()
                    }
                }
                .catch {
                    _state.update {
                        it.errorState()
                    }
                }
                .collect{characterList ->
                    when(characterList){
                        is Either.Error -> _state.update {
                            it.errorEither()
                        }
                        is Either.Success -> _state.update {
                            it.successEither(characterList.data)
                        }
                    }
                }
        }
    }

    fun getCharacterListOrderByNameAZ() {
        viewModelScope.launch {
            listOrderNameGetCharacterUseCase.getCharacterListOrderByNameAZ()
                .onStart {
                    _state.update {
                        it.loadingState()
                    }
                }
                .catch {
                    _state.update {
                        it.errorState()
                    }
                }
                .collect { characterList ->
                    when (characterList) {
                        is Either.Error -> _state.update {
                            it.errorEither()
                        }

                        is Either.Success -> _state.update {
                            it.successEither(characterList.data)
                        }
                    }
                }
        }
    }

    fun getCharacterListOrderByNameZA() {
        viewModelScope.launch {
            listOrderNameGetCharacterUseCase.getCharacterListOrderByNameZA()
                .onStart {
                    _state.update {
                        it.loadingState()
                    }
                }
                .catch {
                    _state.update {
                        it.errorState()
                    }
                }
                .collect { characterList ->
                    when (characterList) {
                        is Either.Error -> _state.update {
                            it.errorEither()
                        }

                        is Either.Success -> _state.update {
                            it.successEither(characterList.data)
                        }
                    }
                }
        }
    }

    fun getCharacterListOrderByFavourites() {
        viewModelScope.launch {
            listOrderNameFavouritesGetCharacterUseCase.getCharacterListOrderFavourites()
                .onStart {
                    _state.update {
                        it.loadingState()
                    }
                }
                .catch {
                    _state.update {
                        it.errorState()
                    }
                }
                .collect { characterList ->
                    when (characterList) {
                        is Either.Error -> _state.update {
                            it.errorEither()
                        }

                        is Either.Success -> _state.update {
                            it.successEither(characterList.data)
                        }
                    }
                }
        }
    }
}