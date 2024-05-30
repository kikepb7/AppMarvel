package com.enriquepalmadev.domain_layer.feature.character.useCase

import com.enriquepalmadev.domain_layer.commons.Either
import com.enriquepalmadev.domain_layer.feature.character.model.CharacterModel
import com.enriquepalmadev.domain_layer.feature.character.repository.CharacterRepository
import com.enriquepalmadev.domain_layer.feature.character.utils.CharacterErrorModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ListOrderFavouritesGetCharacterUseCaseTest{

    private val characterListRepository = mockk<CharacterRepository>(relaxed = true)
    private lateinit var listOrderFavouritesGetCharacterUseCase: ListOrderFavouritesGetCharacterUseCase

    @Before
    fun onBefore(){
        listOrderFavouritesGetCharacterUseCase = ListOrderFavouritesGetCharacterUseCase(characterListRepository)
    }

    @Test
    fun `getCharacterListOrderFavourites success`() = runTest {
        val characterList = listOf(CharacterModel(id = 1, name = "Deadpool", description = "Un tio con Katanas", thumbnailDTO = ""))
        val successResponse = Either.Success(characterList)
        coEvery { characterListRepository.getCharacterList() } returns successResponse

        //When
        val result = listOrderFavouritesGetCharacterUseCase.getCharacterListOrderFavourites().first()
        coVerify {
            characterListRepository.getCharacterList()
        }

        //Then
        assertEquals(result, successResponse)
    }

    @Test
    fun `getCharacterListOrderFavourites error`() = runTest {
        val error = CharacterErrorModel.UnknownHostError
        val errorResponse = Either.Error(CharacterErrorModel.UnknownHostError)
        coEvery { characterListRepository.getCharacterList() } returns errorResponse

        //When
        val result = listOrderFavouritesGetCharacterUseCase.getCharacterListOrderFavourites().toList()

        //Then
        val expected = errorResponse.error
        assertEquals(expected, error)
    }
}