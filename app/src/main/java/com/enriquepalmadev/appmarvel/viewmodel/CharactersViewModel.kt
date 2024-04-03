package com.enriquepalmadev.appmarvel.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enriquepalmadev.appmarvel.model.Character
import com.enriquepalmadev.appmarvel.model.CharacterProvider
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch


class CharactersViewModel: ViewModel() {

    val state = MutableSharedFlow<State>()


    fun getCharacterList(){
        viewModelScope.launch {
            val listCharacter = CharacterProvider.characterList
            state.emit(State.ListReceived(listCharacter))
        }
    }

    //Go to character´s detail
    fun onItemSelected(id : Long){
        viewModelScope.launch {
            state.emit(State.NavigateToDetail(id))
        }
    }

    sealed class State{
        data object Loading : State()
        data object Error : State()
        data class ListReceived(val listCharacters : List<Character>) : State()
        data class NavigateToDetail(val characterId : Long) : State()
    }

}