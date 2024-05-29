package com.enriquepalmadev.domain_layer.feature.character.useCase

import com.enriquepalmadev.domain_layer.commons.Either
import com.enriquepalmadev.domain_layer.commons.eitherSuccess
import com.enriquepalmadev.domain_layer.feature.character.model.CharacterModel
import com.enriquepalmadev.domain_layer.feature.character.repository.CharacterRepository
import com.enriquepalmadev.domain_layer.feature.character.utils.CharacterErrorModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetCharacterUseCaseTest{

    private val characterListRepository= mockk<CharacterRepository>(relaxed = true)

    private val getCharacterUseCase= GetCharacterUseCase(characterListRepository)

    @Before
    fun onBefore(){
        //MockKAnnotations.init(this)
        //getCharacterUseCase = GetCharacterUseCase(characterListRepository)
    }

    @Test
    fun `when repository returns success then use case emits success`() = runBlocking{
        //Given
        val characterList = listOf(CharacterModel(id = 1, name = "Deadpool", description = "Un tio con Katanas", thumbnailDTO = ""))
        coEvery { characterListRepository.getCharacterList() } returns Either.Success<List<CharacterModel>>(characterList)

        //When
        val result = getCharacterUseCase.getCharacterList().toList()

        //Then
        assertEquals(1, result.size)
        assert(result[0] is Either.Success)
        assertEquals(characterList, (result[0] as Either.Success).data)
    }

    @Test
    fun `when repository returns error then use case emits error`() = runBlocking {
        // Given
        val error = CharacterErrorModel.UnknownHostError
        val response = Either.Error(CharacterErrorModel.UnknownHostError)
        coEvery { characterListRepository.getCharacterList() } returns response//Either.Error(error)

        // When
        val result = getCharacterUseCase.getCharacterList().toList()

        // Then
        assertEquals(1, result.size)
        //assert(result[0] is Either.Error)
        //assertEquals(error, (result[0] as Either.Error).error)
    }
}