package com.enriquepalmadev.appmarvel.domain
/*
import com.enriquepalmadev.appmarvel.data.database.CharacterRepository
import com.enriquepalmadev.appmarvel.domain.model.Character
import javax.inject.Inject

class GetRandomQuoteUseCase @Inject constructor(private val repository: CharacterRepository) {

    suspend fun invoke(): Character?{
        val characters = repository.getAllCharactersFromDatabase()

        if(!characters.isNullOrEmpty()){
            val randomNumber = (characters.indices).random()
            return characters[randomNumber]
        }
        return null
    }
}

 */