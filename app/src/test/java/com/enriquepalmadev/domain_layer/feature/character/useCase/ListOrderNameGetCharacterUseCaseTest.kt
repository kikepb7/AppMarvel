package com.enriquepalmadev.domain_layer.feature.character.useCase

import com.enriquepalmadev.domain_layer.commons.Either
import com.enriquepalmadev.domain_layer.feature.character.model.CharacterModel
import com.enriquepalmadev.domain_layer.feature.character.repository.CharacterRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ListOrderNameGetCharacterUseCaseTest{
    private val characterListRepository = mockk<CharacterRepository>(relaxed = true)

    private lateinit var listOrderNameGetCharacterUseCase: ListOrderNameGetCharacterUseCase

    @Before
    fun onBefore(){
        listOrderNameGetCharacterUseCase = ListOrderNameGetCharacterUseCase(characterListRepository)
    }

    @Test
    fun `getCharacterListOrderByNameAZ returns sorted list in ascending order`() = runTest {
        //Given
        val characterList = listOf(
            CharacterModel(id = 1, name = "Deadpool", description = "Un tio con Katanas", thumbnailDTO = ""),
            CharacterModel(id = 2, name = "Spiderman", description = "", thumbnailDTO = ""),
        )
        val successResponse = Either.Success(characterList)
        coEvery { characterListRepository.getCharacterList() } returns successResponse

        //When
        var result = listOrderNameGetCharacterUseCase.getCharacterListOrderByNameAZ().toList()

        //Then
        val expected = Either.Success(characterList.sortedBy { it.name })
        assertEquals(expected, result.first())
        coVerify(exactly = 1) { characterListRepository.getCharacterList() }
        confirmVerified(characterListRepository)
    }

    @Test
    fun `getCharacterListOrderByNameZA returns sorted list in descending order`() = runTest {
        //Given
        val characterList = listOf(
            CharacterModel(id = 1, name = "Deadpool", description = "Un tio con Katanas", thumbnailDTO = ""),
            CharacterModel(id = 2, name = "Spiderman", description = "", thumbnailDTO = ""),
        )
        val successResponse = Either.Success(characterList)
        coEvery { characterListRepository.getCharacterList() } returns successResponse

        //When
        var result = listOrderNameGetCharacterUseCase.getCharacterListOrderByNameZA().toList()

        //Then
        val expected = Either.Success(characterList.sortedByDescending { it.name })
        assertEquals(expected, result.first())
    }
}