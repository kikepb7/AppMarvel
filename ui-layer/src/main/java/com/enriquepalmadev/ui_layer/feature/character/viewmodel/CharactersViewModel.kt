package com.enriquepalmadev.ui_layer.feature.character.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enriquepalmadev.domain_layer.feature.character.model.CharacterModel
import com.enriquepalmadev.domain_layer.feature.character.useCase.FiltListGetCharacterUseCase
import com.enriquepalmadev.domain_layer.feature.character.useCase.GetCharacterUseCase
import com.enriquepalmadev.domain_layer.feature.character.useCase.ListOrderFavouritesGetCharacterUseCase
import com.enriquepalmadev.domain_layer.feature.character.useCase.ListOrderNameGetCharacterUseCase
import com.enriquepalmadev.domain_layer.feature.character.utils.CharacterErrorModel
import com.enriquepalmadev.domain_layer.feature.commons.Either
import com.enriquepalmadev.ui_layer.R
import com.enriquepalmadev.ui_layer.feature.character.view.compose.dataclass.CharacterItemModel
import com.enriquepalmadev.ui_layer.feature.character.view.compose.dataclass.CharacterListModel
import com.enriquepalmadev.ui_layer.feature.character.view.compose.dataclass.CharactersScreenModel
import com.enriquepalmadev.ui_layer.feature.character.view.compose.dataclass.ErrorScreenModel
import com.enriquepalmadev.ui_layer.feature.character.view.compose.dataclass.TitleListModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val getCharacterUseCase : GetCharacterUseCase,
    private val filtListGetCharacterUseCase : FiltListGetCharacterUseCase,
    private val listOrderNameGetCharacterUseCase : ListOrderNameGetCharacterUseCase,
    private val listOrderNameFavouritesGetCharacterUseCase : ListOrderFavouritesGetCharacterUseCase
): ViewModel() {
    private val _state = MutableStateFlow<CharactersScreenModel>(CharactersScreenModel())
    val state = _state.asStateFlow()


    fun getCharacterList(){
        viewModelScope.launch {
            //Para probar lo de los Errores
            getCharacterUseCase.getCharacterList()
                .onStart { _state.emit(CharactersScreenModel(loadingModel = true)) }
                .catch {exception ->
                    _state.emit(CharactersScreenModel(errorScreenModel = ErrorScreenModel(image = R.drawable.deadpool_no_connection, message = exception.message.toString())))
                }
                .collect{characterList ->
                    when(characterList){
                        is Either.Error -> _state.emit(CharactersScreenModel(
                            errorScreenModel = ErrorScreenModel(
                                image = R.drawable.deadpool_no_connection,
                                message = characterList.error.toString()
                            )
                        ))
                        is Either.Success -> _state.emit(CharactersScreenModel(
                            characterListModel = CharacterListModel(
                                titleListModel = TitleListModel(
                                    icon = R.drawable.ironman,
                                    title = R.string.characters_view.toString()
                                ),
                                characterList = characterList.data
                            )
                        ))
                    }
                }
        }
    }

    fun getCharacterFiltList(filt: String){
        viewModelScope.launch {
            filtListGetCharacterUseCase.getCharacterFilterList(filt)
                .onStart { _state.emit(CharactersScreenModel(loadingModel = true)) }
                .catch {exception ->
                    _state.emit(CharactersScreenModel(errorScreenModel = ErrorScreenModel(image = R.drawable.deadpool_no_connection, message = exception.message.toString())))
                }
                .collect{characterList ->
                    when(characterList){
                        is Either.Error -> _state.emit(CharactersScreenModel(
                            errorScreenModel = ErrorScreenModel(
                                image = R.drawable.deadpool_no_connection,
                                message = characterList.error.toString()
                            )
                        ))
                        is Either.Success -> _state.emit(CharactersScreenModel(
                            characterListModel = CharacterListModel(
                                titleListModel = TitleListModel(
                                    icon = R.drawable.ironman,
                                    title = R.string.characters_view.toString()
                                ),
                                characterList = characterList.data
                            )
                        ))
                    }
                }
        }
    }

    fun getCharacterListOrderByName(){
        viewModelScope.launch {
            listOrderNameGetCharacterUseCase.getCharacterListOrderByName()
                .onStart { _state.emit(CharactersScreenModel(loadingModel = true)) }
                .catch {exception ->
                    _state.emit(CharactersScreenModel(errorScreenModel = ErrorScreenModel(image = R.drawable.deadpool_no_connection, message = exception.message.toString())))
                }
                .collect{characterList ->
                    when(characterList){
                        is Either.Error -> _state.emit(CharactersScreenModel(
                            errorScreenModel = ErrorScreenModel(
                                image = R.drawable.deadpool_no_connection,
                                message = characterList.error.toString()
                            )
                        ))
                        is Either.Success -> _state.emit(CharactersScreenModel(
                            characterListModel = CharacterListModel(
                                titleListModel = TitleListModel(
                                    icon = R.drawable.ironman,
                                    title = R.string.characters_view.toString()
                                ),
                                characterList = characterList.data
                            )
                        ))                    }
                }
        }
    }

    fun getCharacterListOrderByFavourites(){
        viewModelScope.launch {
            listOrderNameFavouritesGetCharacterUseCase.getCharacterListOrderFavourites()
                .onStart { _state.emit(CharactersScreenModel(loadingModel = true)) }//Esto sustituye al state
                .catch {exception ->
                    _state.emit(CharactersScreenModel(errorScreenModel = ErrorScreenModel(image = R.drawable.deadpool_no_connection, message = exception.message.toString())))
                }
                .collect{characterList ->
                    when(characterList){
                        is Either.Error -> _state.emit(CharactersScreenModel(
                            errorScreenModel = ErrorScreenModel(
                                image = R.drawable.deadpool_no_connection,
                                message = characterList.error.toString()
                            )
                        ))//Emitir estado de error
                        is Either.Success -> _state.emit(CharactersScreenModel(
                            characterListModel = CharacterListModel(
                                titleListModel = TitleListModel(
                                    icon = R.drawable.ironman,
                                    title = R.string.characters_view.toString()
                                ),
                                characterList = characterList.data
                            )
                        ))
                    }
                }
        }

    }

    sealed class State{
        data object Loading : State()
        data class CharacterError(val error: CharacterErrorModel) : State()//TODO Seguir por aqui.
        data class ListReceived(val listCharacters: List<CharacterModel>?) : State()
        data class NavigateToDetail(val characterId: Int) : State()
        data object Error: State()
    }

}