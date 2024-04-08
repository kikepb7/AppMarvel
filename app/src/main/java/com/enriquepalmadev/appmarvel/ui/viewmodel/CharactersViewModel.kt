package com.enriquepalmadev.appmarvel.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enriquepalmadev.appmarvel.domain.model.Character
import com.enriquepalmadev.appmarvel.domain.model.CharacterProvider
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch


class CharactersViewModel: ViewModel(
    //private val getQuoteUseCase: GetQuoteUseCase,
    //private val getRandomQuoteUseCase: GetRandomQuoteUseCase
) {
    private val _state = MutableSharedFlow<State>()
    val state = _state.asSharedFlow()


    fun getCharacterList(): List<Character>{
        lateinit var listCharacter: List<Character>
        viewModelScope.launch {
            listCharacter = CharacterProvider.characterList

            _state.emit(State.ListReceived(listCharacter))
        }
        return listCharacter
    }

    //Coger los datos del
    /*fun getCharacterListQuote(){
        viewModelScope.launch {
            val listCharacter = getRandomQuoteUseCase()

            _state.emit(State.ListReceived(listCharacter))
        }
        return listCharacter
    }*/

    fun onItemSelected(character: Character){
        viewModelScope.launch {
            _state.emit(State.NavigateToDetail(character))
        }
    }
    sealed class State{
        data object Loading : State()
        data object Error : State()
        data class ListReceived(val listCharacters : List<Character>) : State()
        data class NavigateToDetail(val character: Character) : State()
    }

}