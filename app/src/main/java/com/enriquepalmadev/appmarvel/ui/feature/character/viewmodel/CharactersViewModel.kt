package com.enriquepalmadev.appmarvel.ui.feature.character.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enriquepalmadev.appmarvel.data.feature.character.utils.CharacterError
import com.enriquepalmadev.appmarvel.data.feature.character.utils.Either
import com.enriquepalmadev.appmarvel.domain.feature.character.model.CharacterModel
import com.enriquepalmadev.appmarvel.domain.feature.character.useCase.FiltListGetCharacterUseCase
import com.enriquepalmadev.appmarvel.domain.feature.character.useCase.GetCharacterUseCase
import com.enriquepalmadev.appmarvel.domain.feature.character.useCase.ListOrderFavouritesGetCharacterUseCase
import com.enriquepalmadev.appmarvel.domain.feature.character.useCase.ListOrderNameGetCharacterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch


class CharactersViewModel: ViewModel() {
    private val _state = MutableStateFlow<State>(State.Loading)
    val state = _state.asStateFlow()
    private val getCharacterUseCase = GetCharacterUseCase()
    private val filtListGetCharacterUseCase = FiltListGetCharacterUseCase()
    private val listOrderNameGetCharacterUseCase= ListOrderNameGetCharacterUseCase()
    private val listOrderNameFavouritesGetCharacterUseCase= ListOrderFavouritesGetCharacterUseCase()

    fun getCharacterList(){
        viewModelScope.launch {
            //Para probar lo de los Errores
            getCharacterUseCase.getCharacterList()
                .onStart { _state.emit(State.Loading) }
                .catch {exception ->
                    val error = when(exception){
                        is CharacterError -> CharacterError.ApiError(code = exception.hashCode(), message = exception.message.toString())
                        else -> {CharacterError.UnknownHostError}
                    }
                    _state.emit(State.CharacterError(error))
                }
                .collect{characterList ->
                    when(characterList){
                        is Either.Error -> _state.emit(State.CharacterError(characterList.error))
                        is Either.Success -> _state.emit(State.ListReceived(characterList.data))
                    }
                }
        }
    }

    fun getCharacterFiltList(filt: String){
        viewModelScope.launch {
            filtListGetCharacterUseCase.getCharacterFilterList(filt)
                .onStart { _state.emit(State.Loading) }
                .catch {exception ->
                    val error = when(exception){
                        is CharacterError -> CharacterError.ApiError(code = exception.hashCode(), message = exception.message.toString())
                        else -> {CharacterError.UnknownHostError}
                    }
                    _state.emit(State.CharacterError(error))
                }
                .collect{characterList ->
                    when(characterList){
                        is Either.Error -> _state.emit(State.CharacterError(characterList.error))//Emitir estado de error
                        is Either.Success -> _state.emit(State.ListReceived(characterList.data))
                    }
                }
        }
    }

    fun getCharacterListOrderByName(){
        viewModelScope.launch {
            listOrderNameGetCharacterUseCase.getCharacterListOrderByName()
                .onStart { _state.emit(State.Loading) }
                .catch {exception ->
                    val error = when(exception){
                        is CharacterError -> CharacterError.ApiError(code = exception.hashCode(), message = exception.message.toString())
                        else -> {CharacterError.UnknownHostError}
                    }
                    _state.emit(State.CharacterError(error))
                }
                .collect{characterList ->
                    when(characterList){
                        is Either.Error -> _state.emit(State.CharacterError(characterList.error))//Emitir estado de error
                        is Either.Success -> _state.emit(State.ListReceived(characterList.data))
                    }
                }
        }
    }

    fun getCharacterListOrderByFavourites(){
        viewModelScope.launch {
            listOrderNameFavouritesGetCharacterUseCase.getCharacterListOrderFavourites()
                .onStart { _state.emit(State.Loading) }
                .catch {exception ->
                    val error = when(exception){
                        is CharacterError -> CharacterError.ApiError(code = exception.hashCode(), message = exception.message.toString())
                        else -> {CharacterError.UnknownHostError}
                    }
                    _state.emit(State.CharacterError(error))
                }//TODO corregir error isntanciarlo aqui CharacterError
                .collect{characterList ->
                    when(characterList){
                        is Either.Error -> _state.emit(State.CharacterError(characterList.error))//Emitir estado de error
                        is Either.Success -> _state.emit(State.ListReceived(characterList.data))
                    }
                }
        }

    }

    fun done(){
        viewModelScope.launch {

        }
    }

    fun onItemSelected(characterId: Int){
        viewModelScope.launch {
            _state.emit(State.NavigateToDetail(characterId))
        }
    }

    sealed class State{
        data object Loading : State()
        data class CharacterError(val error: com.enriquepalmadev.appmarvel.data.feature.character.utils.CharacterError) : State()//TODO Seguir por aqui.
        data class Error(val error: String) : State()//Quitar/Poner el catch
        data class ListReceived(val listCharacters: List<CharacterModel>?) : State()
        data class NavigateToDetail(val characterId: Int) : State()
    }

}