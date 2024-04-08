package com.enriquepalmadev.appmarvel.domain
/*
import com.enriquepalmadev.appmarvel.data.database.CharacterRepository
import com.enriquepalmadev.appmarvel.data.database.entities.toDatabase
import com.enriquepalmadev.appmarvel.domain.model.Character
import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(private val repository: CharacterRepository) {
    suspend operator fun invoke(): List<Character>{
        val characters = repository.getAllCharactersFromApi()

        return if(characters.isNotEmpty()){

            //If it is not empty We will use the cloud data
            repository.clearCharacters()
            repository.insertCharacters(characters.map { it.toDatabase() })
            characters
        }else{
            //In case de Api not run We will use the local data
            repository.getAllCharactersFromDatabase()
        }
    }
}
*/
