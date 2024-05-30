package com.enriquepalmadev.domain_layer.feature.character.useCase

import com.enriquepalmadev.domain_layer.commons.Either
import com.enriquepalmadev.domain_layer.feature.character.model.CharacterModel
import com.enriquepalmadev.domain_layer.feature.character.repository.CharacterRepository
import com.enriquepalmadev.domain_layer.feature.character.utils.CharacterErrorModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetCharacterDetailUseCaseTest{

    private var characterRepository = mockk<CharacterRepository>()
    private lateinit var getCharacterDetailUseCase: GetCharacterDetailUseCase

    @Before
    fun onBefore(){
        getCharacterDetailUseCase = GetCharacterDetailUseCase(characterRepository)
    }


    @Test
    fun `getCharacterDetail success`() = runTest {
        // Given
        val characterId = 1
        val character = CharacterModel(
            id = characterId,
            name = "Deadpool",
            description = "Un man con katanas",
            thumbnailDTO = ""
        )
        val successResponse = Either.Success(character)
        coEvery { characterRepository.getCharacterDetail(characterId) } returns successResponse

        // When
        val result = getCharacterDetailUseCase.getCharacterDetail(characterId).first()

        // Then
        assertEquals(successResponse, result)
    }

    @Test
    fun `getCharacterDetail error`() = runTest {
        // Given
        val characterId = 1
        val error = CharacterErrorModel.UnknownHostError
        val errorResponse = Either.Error(error)
        coEvery { characterRepository.getCharacterDetail(characterId) } returns errorResponse

        // When
        val result = getCharacterDetailUseCase.getCharacterDetail(characterId).first()

        // Then
        assertEquals(errorResponse, result)
    }
}