package com.enriquepalmadev.data_layer.feature.character.repository

import com.enriquepalmadev.data_layer.feature.character.datasource.CharacterRemoteDataSource
import com.enriquepalmadev.data_layer.feature.character.dto.CharacterResponseDTO
import com.enriquepalmadev.data_layer.feature.character.dto.DataDTO
import com.enriquepalmadev.data_layer.feature.character.dto.ResultDTO
import com.enriquepalmadev.data_layer.feature.character.dto.ThumbnailDTO
import com.enriquepalmadev.data_layer.feature.character.utils.CharacterError
import com.enriquepalmadev.data_layer.feature.character.utils.extensions.toCharacterListModel
import com.enriquepalmadev.domain_layer.commons.Either
import com.enriquepalmadev.domain_layer.feature.character.utils.CharacterErrorModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CharacterRepositoryImplTest{
    private val characterRemoteDataSource = mockk<CharacterRemoteDataSource>()
    private lateinit var characterRepositoryImpl: CharacterRepositoryImpl

    @Before
    fun onBefore(){
        characterRepositoryImpl = CharacterRepositoryImpl(characterRemoteDataSource)
    }

    //getList
    @Test
    fun `getCharacterList success`() = runTest {
        // Given
        val resultList = listOf(
            ResultDTO(
                description = "Un tio con katanas",
                id = 1.toString(),
                modified = "",
                name = "Deadpool",
                resourceURI = "",
                thumbnail = ThumbnailDTO("", ""),
                urls = listOf()
            ),
            ResultDTO(
                description = "Un tio medio araña",
                id = 2.toString(),
                modified = "",
                name = "Spider-man",
                resourceURI = "",
                thumbnail = ThumbnailDTO("", ""),
                urls = listOf()
            )
        )
        val dataDTO = DataDTO(
            count = "2",
            limit = "20",
            offset = "0",
            results = resultList,
            total = "2"
        )
        val expectedList = resultList.toCharacterListModel()
        val responseDTO = CharacterResponseDTO<ResultDTO>(data = dataDTO)
        val expectedResult: Either<CharacterError, CharacterResponseDTO<ResultDTO>> = Either.Success(responseDTO)
        coEvery { characterRemoteDataSource.getCharactersFromApi() } returns expectedResult

        // When
        val result = characterRepositoryImpl.getCharacterList()

        // Then
        assertTrue(result is Either.Success)
        assertEquals((result as Either.Success).data, expectedList)
        coVerify(exactly = 1) { characterRemoteDataSource.getCharactersFromApi() }
    }

    @Test
    fun `getCharacterList error`() = runTest {

        // Given
        val error = Either.Error(CharacterError.UnknownHostError)
        val expectedResult: Either<CharacterError, CharacterResponseDTO<ResultDTO>> = error
        coEvery { characterRemoteDataSource.getCharactersFromApi() } returns expectedResult

        // When
        val result = characterRepositoryImpl.getCharacterList()

        // Then
        assertEquals(Either.Error(CharacterErrorModel.UnknownHostError), result)
        coVerify { characterRemoteDataSource.getCharactersFromApi() }
    }

    //getDetail
    @Test
    fun `getCharacterDetail success`() = runTest {
        // Given
        val resultList = listOf(
            ResultDTO(
                description = "Un tio con katanas",
                id = 1.toString(),
                modified = "",
                name = "Deadpool",
                resourceURI = "",
                thumbnail = ThumbnailDTO("", ""),
                urls = listOf()
            ),
            ResultDTO(
                description = "Un tio medio araña",
                id = 2.toString(),
                modified = "",
                name = "Spider-man",
                resourceURI = "",
                thumbnail = ThumbnailDTO("", ""),
                urls = listOf()
            )
        )
        val dataDTO = DataDTO(
            count = "2",
            limit = "20",
            offset = "0",
            results = resultList,
            total = "2"
        )
        val expectedCharacter = resultList.toCharacterListModel()?.find {
            it.id == 1
        }
        val responseDTO = CharacterResponseDTO<ResultDTO>(data = dataDTO)
        val expectedResult: Either<CharacterError, CharacterResponseDTO<ResultDTO>> = Either.Success(responseDTO)
        coEvery { characterRemoteDataSource.getCharacterDetailFromApi(1) } returns expectedResult

        // When
        val result = characterRepositoryImpl.getCharacterDetail(1)

        // Then
        assertEquals(Either.Success(expectedCharacter), result)
    }

    @Test
    fun `getCharacterDetail error`() = runTest {
        // Given
        val error = Either.Error(CharacterError.UnknownHostError)
        val expectedResult: Either<CharacterError, CharacterResponseDTO<ResultDTO>> = error
        coEvery { characterRemoteDataSource.getCharacterDetailFromApi(1) } returns expectedResult

        // When
        val result = characterRepositoryImpl.getCharacterDetail(1)

        // Then
        assertEquals(Either.Error(CharacterErrorModel.UnknownHostError), result)
        coVerify { characterRemoteDataSource.getCharacterDetailFromApi(1) }
    }
}