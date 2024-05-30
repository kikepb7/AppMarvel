package com.enriquepalmadev.domain_layer.feature.character.useCase

import com.enriquepalmadev.domain_layer.commons.Either
import com.enriquepalmadev.domain_layer.feature.character.model.CharacterModel
import com.enriquepalmadev.domain_layer.feature.character.repository.CharacterRepository
import com.enriquepalmadev.domain_layer.feature.character.utils.CharacterErrorModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class GetCharacterUseCaseTest{

    private val characterListRepository = mockk<CharacterRepository>(relaxed = true)

    private lateinit var getCharacterUseCase: GetCharacterUseCase

    @Before
    fun onBefore(){
        getCharacterUseCase= GetCharacterUseCase(characterListRepository)
    }

    //@get:Rule  val coroutineRule = MainCoroutineRule()

    @Test
    fun `when repository returns success then use case emits success`() = runTest(UnconfinedTestDispatcher()){
        //Given
        val characterList = listOf(CharacterModel(id = 1, name = "Deadpool", description = "Un tio con Katanas", thumbnailDTO = ""))
        val successResponse = Either.Success(characterList)
        coEvery { characterListRepository.getCharacterList() } returns successResponse

        //When
        val result = getCharacterUseCase.getCharacterList().first()
        coVerify {
            characterListRepository.getCharacterList()
        }

        //Then
        assertEquals(result, successResponse)
    }

    @Test
    fun `when repository returns error then use case emits error`() = runBlocking {
        // Given
        val error = CharacterErrorModel.UnknownHostError
        val errorResponse = Either.Error(CharacterErrorModel.UnknownHostError)
        coEvery { characterListRepository.getCharacterList() } returns errorResponse//Either.Error(error)

        // When
        val result = getCharacterUseCase.getCharacterList().toList()

        // Then
        assertEquals(1, result.size)
        assert(result[0] is Either.Error)
        assertEquals(error, (result[0] as Either.Error).error)
    }
}
class MainCoroutineRule(
    private val dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
) : TestWatcher() {
    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(dispatcher)
    }
    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
        dispatcher.cleanupTestCoroutines()
    }
}

