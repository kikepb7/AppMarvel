package com.enriquepalmadev.appmarvel.data.feature.character.datasource

import com.enriquepalmadev.appmarvel.data.feature.character.dto.CharacterResponseDTO
import com.enriquepalmadev.appmarvel.data.feature.character.dto.ResultDTO
import com.enriquepalmadev.appmarvel.data.feature.character.utils.CharacterError
import com.enriquepalmadev.appmarvel.data.feature.character.utils.Constants
import com.enriquepalmadev.appmarvel.data.feature.character.utils.Either
import retrofit2.Response
import java.io.IOException

class CharacterRemoteDataSource: CharacterDataSource {

    private val retrofit = Retrofit.retrofitService

    override suspend fun getCharactersFromApi(): Either<CharacterError, CharacterResponseDTO<ResultDTO>> {

        val response: Response<CharacterResponseDTO<ResultDTO>> = retrofit.getAllCharacters(hash = Constants.HASH, ts = Constants.TS, limit = 100)
        val body = response.body()
        return try {
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
            Either.Error(CharacterError.ApiError(code = response.code(), message = response.errorBody().toString()))
        }catch (error: Exception){
            Either.Error(CharacterError.ApiError(code = response.code(), message = response.errorBody().toString()))
        }
        //TODO Tipos de Errores
    }

    override suspend fun getCharacterDetailFromApi(characterId: Int): Either<CharacterError, CharacterResponseDTO<ResultDTO>>{
        val response: Response<CharacterResponseDTO<ResultDTO>> = retrofit.getCharacterById(characterId = characterId, hash = Constants.HASH, ts = Constants.TS)
        val body = response.body()
        return try {
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
            Either.Error(CharacterError.ApiError(code = response.code(), message = response.errorBody().toString()))
        }catch (error: Exception){
            Either.Error(CharacterError.ApiError(code = response.code(), message = response.errorBody().toString()))
        }
    }
}