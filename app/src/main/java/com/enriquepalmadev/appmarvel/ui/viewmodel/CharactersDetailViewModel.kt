package com.enriquepalmadev.appmarvel.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enriquepalmadev.appmarvel.domain.model.CharacterModel
import com.enriquepalmadev.appmarvel.domain.useCase.FetchCharacterDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CharactersDetailViewModel : ViewModel() {
    private val fetchCharacterDetailUseCase = FetchCharacterDetailUseCase()

    //It is initialized with the state of Loading
    val state = MutableStateFlow<DetailState>(DetailState.Loading)


    fun getCharacterDetail(characterId : Int){
        //If the character detail is found, a new state is emitted to the MutableStateFlow
        viewModelScope.launch {
            val characterDetail = fetchCharacterDetailUseCase.fetchCharacterDetail(characterId)

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
    data class  CharacterDetail(val character: CharacterModel) : DetailState()
}