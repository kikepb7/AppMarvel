package com.enriquepalmadev.domain_layer.feature.character.useCase

import com.enriquepalmadev.domain_layer.commons.Either
import com.enriquepalmadev.domain_layer.feature.character.model.CharacterModel
import com.enriquepalmadev.domain_layer.feature.character.repository.CharacterRepository
import com.enriquepalmadev.domain_layer.feature.character.utils.CharacterErrorModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class FiltListGetCharacterUseCaseTest{
    private val characterListRepository = mockk<CharacterRepository>(relaxed = true)
    private lateinit var filtListGetCharacterUseCase: FiltListGetCharacterUseCase

    @Before
    fun onBefore(){
        filtListGetCharacterUseCase = FiltListGetCharacterUseCase(characterListRepository)
    }

    @Test
    fun `getCharacterFilterList returns filtered list on success`() = runTest {
        //Given
        val characterList = listOf(
            CharacterModel(id = 1, name = "Deadpool", description = "Un tio con Katanas", thumbnailDTO = ""),
            CharacterModel(id = 2, name = "Spiderman", description = "", thumbnailDTO = ""),
        )
        val successResponse = Either.Success(characterList)
        coEvery { characterListRepository.getCharacterList() } returns successResponse

        //When
        var result = filtListGetCharacterUseCase.getCharacterFilterList("Deadpool").first()

        //Then
        val expected = Either.Success(listOf(CharacterModel(id = 1, name = "Deadpool", description = "Un tio con Katanas", thumbnailDTO = "")))
        assertEquals(expected, result)
        coVerify(exactly = 1) { characterListRepository.getCharacterList() }
        confirmVerified(characterListRepository)
    }

    @Test
    fun `getCharacterFilterList returns error on repository error`() = runTest {
        // Given
        val errorResponse = Either.Error(CharacterErrorModel.UnknownHostError)
        coEvery { characterListRepository.getCharacterList() } returns errorResponse

        // When
        val result = filtListGetCharacterUseCase.getCharacterFilterList("Deadpool").toList()

        // Then
        val expected = listOf(errorResponse)
        assertEquals(expected, result)
    }
}