package com.enriquepalmadev.appmarvel.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enriquepalmadev.appmarvel.domain.model.CharacterModel
import com.enriquepalmadev.appmarvel.domain.useCase.FetchCharacterUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch


class CharactersViewModel: ViewModel() {
    private val _state = MutableSharedFlow<State>()
    val state = _state.asSharedFlow()
    private val fetchCharacterUseCase = FetchCharacterUseCase()

    fun getCharacterList(){
        var listCharacter: List<CharacterModel>?
        viewModelScope.launch {
            listCharacter = fetchCharacterUseCase.fetchCharacterList()

            _state.emit(State.ListReceived(listCharacter))
        }
    }

    fun getCharacterFiltList(filt: String){
        var listCharacter: List<CharacterModel>?
        viewModelScope.launch {
            listCharacter = fetchCharacterUseCase.fetchCharacterFilterList(filt)

            _state.emit(State.ListReceived(listCharacter))
        }
    }

    fun getCharacterListOrderByName(){
        var listCharacter: List<CharacterModel>?
        viewModelScope.launch {
            listCharacter = fetchCharacterUseCase.fetchCharacterListOrderName()

            _state.emit(State.ListReceived(listCharacter))
        }
    }

    fun getCharacterListOrderByFavourites(){
        var listCharacter: List<CharacterModel>?
        viewModelScope.launch {
            listCharacter = fetchCharacterUseCase.fetchCharacterListOrderFavourites()

            _state.emit(State.ListReceived(listCharacter))
        }
    }

    fun onItemSelected(character: CharacterModel){
        viewModelScope.launch {
            _state.emit(State.NavigateToDetail(character))
        }
    }
    sealed class State{
        data object Loading : State()
        data object Error : State()
        data class ListReceived(val listCharacters: List<CharacterModel>?) : State()
        data class NavigateToDetail(val character: CharacterModel) : State()
    }

}