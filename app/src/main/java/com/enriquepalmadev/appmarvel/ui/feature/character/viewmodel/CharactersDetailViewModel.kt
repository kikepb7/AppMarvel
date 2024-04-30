package com.enriquepalmadev.appmarvel.ui.feature.character.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enriquepalmadev.appmarvel.data.feature.character.utils.CharacterError
import com.enriquepalmadev.appmarvel.data.feature.character.utils.Either
import com.enriquepalmadev.appmarvel.domain.feature.character.model.CharacterModel
import com.enriquepalmadev.appmarvel.domain.feature.character.useCase.GetCharacterDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class CharactersDetailViewModel : ViewModel() {
    //It is initialized with the state of Loading
    private val _state = MutableStateFlow<DetailState>(DetailState.Loading)
    val state = _state.asStateFlow()
    private val getCharacterDetailUseCase = GetCharacterDetailUseCase()

    fun getCharacterDetail(characterId : Int){
        //If the character detail is found, a new state is emitted to the MutableStateFlow
        viewModelScope.launch {
            getCharacterDetailUseCase.getCharacterDetail(characterId)
                .onStart { _state.emit(DetailState.Loading) }
                .catch {exception ->
                    /*val error = when(exception){
                        is CharacterError -> CharacterError.ApiError(code = exception.hashCode(), message = exception.message.toString())
                        else -> {CharacterError.UnknownHostError}
                    }
                    _state.emit(DetailState.CharacterError(error))
                     */
                    _state.emit(DetailState.Error)
                }
                .collect{either ->
                    when(either){
                        is Either.Error -> _state.emit(DetailState.CharacterError(either.error))
                        is Either.Success -> _state.emit(DetailState.CharacterDetail(either.data))
                    }
                }

        }
    }

    //This class define the different possible states for CharacterDetails
    sealed class DetailState{
        data object Loading : DetailState()
        data class CharacterError(val error: com.enriquepalmadev.appmarvel.data.feature.character.utils.CharacterError): DetailState()
        data class  CharacterDetail(val character: CharacterModel?) : DetailState()
        data object Error: DetailState()
    }
}