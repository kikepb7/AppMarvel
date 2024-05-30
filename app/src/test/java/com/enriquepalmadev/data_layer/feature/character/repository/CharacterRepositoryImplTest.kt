package com.enriquepalmadev.data_layer.feature.character.repository

import com.enriquepalmadev.data_layer.feature.character.datasource.CharacterRemoteDataSource
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class CharacterRepositoryImplTest{
    private var characterRemoteDataSource = mockk<CharacterRemoteDataSource>()
    private lateinit var characterRepositoryImpl: CharacterRepositoryImpl

    @Before
    fun onBefore(){
        characterRepositoryImpl = CharacterRepositoryImpl(characterRemoteDataSource)
    }

    //getList
    @Test
    fun `getCharacterList success`() = runTest {
        /*
        // Given
        val resultDTOList  = listOf(
            ResultDTO("Un man con katanas", "1", "2021-01-01", "Deadpool", "", ThumbnailDTO("jpg", "path/to/image"), emptyList()),
            ResultDTO("Un man con tela-araña", "2", "2021-01-01", "Spider-man", "", ThumbnailDTO("jpg", "path/to/image"), emptyList())
        )
        val dataDTO = DataDTO("2", "10", "0", resultDTOList, "2")
        val characterResponse = CharacterResponseDTO(dataDTO)
        val successResponse = Either.Success(characterResponse)
        coEvery { characterRemoteDataSource.getCharactersFromApi() } returns successResponse

        // When
        val result = characterRepositoryImpl.getCharacterList()

        // Then
        coVerify { characterRemoteDataSource.getCharactersFromApi() }
        val expectedCharacterList = resultDTOList.toCharacterModelList()
        assertEquals(Either.Success(expectedCharacterList), result)

         */
    }

    @Test
    fun `getCharacterList error`() = runTest {
        /*
        // Given
        val errorResponse = Either.Error(CharacterErrorModel.UnknownHostError)
        coEvery { characterRemoteDataSource.getCharactersFromApi() } returns errorResponse

        // When
        val result = characterRepositoryImpl.getCharacterList()

        // Then
        coVerify { characterRemoteDataSource.getCharactersFromApi() }
        assertEquals(Either.Error(CharacterErrorModel.UnknownHostError), result)

         */
    }

    //getDetail
    @Test
    fun `getCharacterDetail success`() = runTest {
        /*
        // Given
        val characterId = 1
        val character = CharacterModel(characterId, "Deadpool", "Un man con katanas", "")
        val successResponse = Either.Success(character)
        coEvery { characterRemoteDataSource.getCharacterDetailFromApi(characterId) } returns successResponse

        // When
        val result = characterRepositoryImpl.getCharacterDetail(characterId)

        // Then
        assertEquals(successResponse, result)

         */
    }

    @Test
    fun `getCharacterDetail error`() = runTest {
        /*
        // Given
        val characterId = 1
        val error = CharacterErrorModel.UnknownHostError
        val errorResponse = Either.Error(error)
        coEvery { characterRemoteDataSource.getCharacterDetailFromApi(characterId) } returns errorResponse

        // When
        val result = characterRepositoryImpl.getCharacterDetail(characterId)

        // Then
        assertEquals(errorResponse, result)

         */
    }
}