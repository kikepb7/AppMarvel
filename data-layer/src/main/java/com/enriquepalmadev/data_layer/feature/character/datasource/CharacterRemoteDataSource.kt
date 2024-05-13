package com.enriquepalmadev.data_layer.feature.character.datasource

import com.enriquepalmadev.data_layer.feature.character.dto.CharacterResponseDTO
import com.enriquepalmadev.data_layer.feature.character.dto.ResultDTO
import com.enriquepalmadev.data_layer.feature.character.utils.CharacterError
import com.enriquepalmadev.domain_layer.feature.commons.Either
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class CharacterRemoteDataSource @Inject constructor(private val service: CharacterService):
    CharacterDataSource {

    // private val retrofit = Retrofit.retrofitService

    override suspend fun getCharactersFromApi(): Either<CharacterError, CharacterResponseDTO<ResultDTO>> {

        return try {
            val response: Response<CharacterResponseDTO<ResultDTO>> = service.getAllCharacters()
            val body = response.body()
            if(response.isSuccessful && body != null){
                Either.Success(body)
            }else{
                if(response.code() == 400){
                    Either.Error(CharacterError.UnknownHostError) //Error 400: Host desconocido.
                }else if(response.code() == 401){
                    Either.Error(CharacterError.Unauthorized) // Error 401: No tienes autoridad para conseguir la lista.
                }else{
                    Either.Error(CharacterError.ApiError(code = response.code(), message = response.errorBody().toString()))
                }
            }
        }catch (error: IOException){
            Either.Error(CharacterError.ApiError(code = error.hashCode(), message = error.message.toString()))
        }catch (error: Exception){
            Either.Error(CharacterError.ApiError(code = error.hashCode(), message = error.message.toString()))
        }
    }

    override suspend fun getCharacterDetailFromApi(characterId: Int): Either<CharacterError, CharacterResponseDTO<ResultDTO>> {

        return try {
            val response: Response<CharacterResponseDTO<ResultDTO>> = service.getCharacterById()
            val body = response.body()
            if(response.isSuccessful && body != null){
                Either.Success(body)
            }else{
                if(response.code() == 400){
                    Either.Error(CharacterError.UnknownHostError) //Error 400: Host desconocido.
                }else if(response.code() == 401){
                    Either.Error(CharacterError.Unauthorized) // Error 401: No tienes autoridad para conseguir la lista.
                }else{
                    Either.Error(CharacterError.ApiError(code = response.code(), message = response.errorBody().toString()))
                }
            }
        }catch (error: IOException){
            Either.Error(CharacterError.ApiError(code = error.hashCode(), message = error.message.toString()))
        }catch (error: Exception){
            Either.Error(CharacterError.ApiError(code = error.hashCode(), message = error.message.toString()))
        }
    }
}