package com.enriquepalmadev.appmarvel.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enriquepalmadev.appmarvel.domain.model.Character
import com.enriquepalmadev.appmarvel.domain.model.CharacterProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CharactersDetailViewModel : ViewModel() {

    //It is initialized with the state of Loading
    val state = MutableStateFlow<DetailState>(DetailState.Loading)


    fun getCharacterDetail(characterId : Long){
        //If the character detail is found, a new state is emitted to the MutableStateFlow
        viewModelScope.launch {
            val characterDetail = CharacterProvider.characterList.find {
                it.id.toInt() == characterId.toInt()
            }

            characterDetail?.let {
                state.emit(DetailState.CharacterDetail(it))
            }
        }
    }
}

//This class define the different possible states for CharacterDetails
sealed class DetailState{
    data object Loading : DetailState()
    data object Error : DetailState()
    data class  CharacterDetail(val character: Character) : DetailState()
}