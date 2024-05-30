package com.enriquepalmadev.ui_layer.feature.character.viewmodel

import com.enriquepalmadev.domain_layer.commons.Either
import com.enriquepalmadev.domain_layer.feature.character.model.CharacterModel
import com.enriquepalmadev.domain_layer.feature.character.useCase.FiltListGetCharacterUseCase
import com.enriquepalmadev.domain_layer.feature.character.useCase.GetCharacterUseCase
import com.enriquepalmadev.domain_layer.feature.character.useCase.ListOrderFavouritesGetCharacterUseCase
import com.enriquepalmadev.domain_layer.feature.character.useCase.ListOrderNameGetCharacterUseCase
import com.enriquepalmadev.domain_layer.feature.character.utils.CharacterErrorModel
import com.enriquepalmadev.ui_layer.R
import com.enriquepalmadev.ui_layer.feature.character.view.compose.dataclass.CharacterListModel
import com.enriquepalmadev.ui_layer.feature.character.view.compose.dataclass.CharactersUIModel
import com.enriquepalmadev.ui_layer.feature.character.view.compose.dataclass.ErrorScreenModel
import com.enriquepalmadev.ui_layer.feature.character.view.compose.dataclass.TitleListModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class CharactersViewModelTest{

    private lateinit var viewModel: CharactersViewModel
    private lateinit var getCharacterUseCase: GetCharacterUseCase
    private lateinit var filtListGetCharacterUseCase: FiltListGetCharacterUseCase
    private lateinit var listOrderNameGetCharacterUseCase: ListOrderNameGetCharacterUseCase
    private lateinit var listOrderNameFavouritesGetCharacterUseCase: ListOrderFavouritesGetCharacterUseCase

    @Before
    fun onBefore(){
        Dispatchers.setMain(Dispatchers.Unconfined)

        getCharacterUseCase = mockk(relaxed = true)
        filtListGetCharacterUseCase = mockk(relaxed = true)
        listOrderNameGetCharacterUseCase = mockk(relaxed = true)
        listOrderNameFavouritesGetCharacterUseCase = mockk(relaxed = true)

        viewModel = CharactersViewModel(
            getCharacterUseCase,
            filtListGetCharacterUseCase,
            listOrderNameGetCharacterUseCase,
            listOrderNameFavouritesGetCharacterUseCase
        )
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    //GetCharacterUseCase
    @Test
    fun `test getCharacterList success`(){
        //Given
        val characterList = listOf(
            CharacterModel(
                id = 1,
                name = "Spider-Man",
                thumbnailDTO = "",
                description = "Un man que laza tela-arañas"
            ),
            CharacterModel(
                id = 2,
                name = "Iron Man",
                thumbnailDTO = "",
                description = "Un man filántropo"
            )
        )
        val expectedCharactersUIModel = CharactersUIModel(
            loadingModel=false,
            errorScreenModel = ErrorScreenModel(image=2131165321, message=2131820837.toString()),
            characterListModel=null,
            headerCharacterListModel=null
        )

        coEvery { getCharacterUseCase.getCharacterList() } returns flow { Either.Success(characterList) }

        //When
        viewModel.getCharacterList()

        //Then
        assertEquals(expectedCharactersUIModel, viewModel.state.value)

    }

    @Test
    fun `test getCharacterList error`(){
        //Given
        val characterList = listOf(
            CharacterModel(
                id = 1,
                name = "Spider-Man",
                thumbnailDTO = "",
                description = "Un man que laza tela-arañas"
            ),
            CharacterModel(
                id = 2,
                name = "Iron Man",
                thumbnailDTO = "",
                description = "Un man filántropo"
            )
        )
        val expectedCharactersUIModel = CharactersUIModel(
            loadingModel=false,
            errorScreenModel = ErrorScreenModel(image=2131165321, message=2131820837.toString()),
            characterListModel=null,
            headerCharacterListModel=null
        )

        coEvery { getCharacterUseCase.getCharacterList() } returns flow { Either.Success(characterList) }

        //When
        viewModel.getCharacterList()

        //Then
        assertEquals(expectedCharactersUIModel, viewModel.state.value)

    }

    //FiltListGetCharacterUseCase

    @Test
    fun `test getCharacterFiltList success`() {
        // Given
        val filt = "Spider"
        val characterList = listOf(
            CharacterModel(
                id = 1,
                name = "Spider-Man",
                thumbnailDTO = "",
                description = "Un man que laza tela-arañas"
            )
        )
        val expectedCharactersUIModel = CharactersUIModel(
            loadingModel = false,
            characterListModel = CharacterListModel(
                titleListModel = TitleListModel(
                    icon = R.drawable.ironman,
                    title = R.string.characters_view.toString()
                ),
                characterList = characterList
            ),
            errorScreenModel = null
        )

        coEvery { filtListGetCharacterUseCase.getCharacterFilterList(filt) } returns flow {
            emit(Either.Success(characterList))
        }

        // When
        viewModel.getCharacterFiltList(filt)

        // Then
        assertEquals(expectedCharactersUIModel, viewModel.state.value)
    }

    @Test
    fun `test getCharacterFiltList error`() {
        // Given
        val filt = "Spider"
        val expectedCharactersUIModel = CharactersUIModel(
            loadingModel = false,
            errorScreenModel = ErrorScreenModel(
                image=2131165321,
                message=2131820837.toString()
            ),
            characterListModel = null
        )

        coEvery { filtListGetCharacterUseCase.getCharacterFilterList(filt) } returns flow {
            emit(Either.Error(CharacterErrorModel.UnknownHostError))
        }

        // When
        viewModel.getCharacterFiltList(filt)

        // Then
        assertEquals(expectedCharactersUIModel, viewModel.state.value)
    }

    //ListOrderNameGetCharacterUseCase - A-Z

    @Test
    fun `test getCharacterListOrderByNameAZ success`() {
        // Given
        val characterList = listOf(
            CharacterModel(
                id = 1,
                name = "Ant-Man",
                thumbnailDTO = "",
                description = "Un hombre que se vuelve pequeño"
            ),
            CharacterModel(
                id = 2,
                name = "Black Panther",
                thumbnailDTO = "",
                description = "El rey de Wakanda"
            ),
            CharacterModel(
                id = 3,
                name = "Spider-Man",
                thumbnailDTO = "",
                description = "Un hombre que lanza telarañas"
            )
        )
        val expectedCharactersUIModel = CharactersUIModel(
            loadingModel = false,
            characterListModel = CharacterListModel(
                titleListModel = TitleListModel(
                    icon = R.drawable.ironman,
                    title = R.string.characters_view.toString()
                ),
                characterList = characterList
            ),
            errorScreenModel = null
        )

        coEvery { listOrderNameGetCharacterUseCase.getCharacterListOrderByNameAZ() } returns flow {
            emit(Either.Success(characterList))
        }

        // When
        viewModel.getCharacterListOrderByNameAZ()

        // Then
        assertEquals(expectedCharactersUIModel, viewModel.state.value)
    }

    @Test
    fun `test getCharacterListOrderByNameAZ error`() {
        // Given
        val expectedCharactersUIModel = CharactersUIModel(
            loadingModel = false,
            errorScreenModel = ErrorScreenModel(
                image = R.drawable.deadpool_no_connection,
                message = R.string.unknownError.toString()
            ),
            characterListModel = null
        )

        coEvery { listOrderNameGetCharacterUseCase.getCharacterListOrderByNameAZ() } returns flow {
            emit(Either.Error(CharacterErrorModel.UnknownHostError))
        }

        // When
        viewModel.getCharacterListOrderByNameAZ()

        // Then
        assertEquals(expectedCharactersUIModel, viewModel.state.value)
    }

    //ListOrderNameGetCharacterUseCase - Z-A

    @Test
    fun `test getCharacterListOrderByNameZA success`() {
        // Given
        val characterList = listOf(
            CharacterModel(
                id = 1,
                name = "Ant-Man",
                thumbnailDTO = "",
                description = "Un hombre que se vuelve pequeño"
            ),
            CharacterModel(
                id = 2,
                name = "Black Panther",
                thumbnailDTO = "",
                description = "El rey de Wakanda"
            ),
            CharacterModel(
                id = 3,
                name = "Spider-Man",
                thumbnailDTO = "",
                description = "Un hombre que lanza telarañas"
            )
        )
        val expectedCharactersUIModel = CharactersUIModel(
            loadingModel=true,
            errorScreenModel=null,
            characterListModel=null,
            headerCharacterListModel=null
        )

        coEvery { listOrderNameGetCharacterUseCase.getCharacterListOrderByNameZA() } returns flow {
            emit(Either.Success(characterList))
        }

        // When
        viewModel.getCharacterListOrderByNameAZ()

        // Then
        assertEquals(expectedCharactersUIModel, viewModel.state.value)
    }

    @Test
    fun `test getCharacterListOrderByNameZA error`() {
        // Given
        val expectedCharactersUIModel = CharactersUIModel(loadingModel=true, errorScreenModel=null, characterListModel=null, headerCharacterListModel=null)

        coEvery { listOrderNameGetCharacterUseCase.getCharacterListOrderByNameZA() } returns flow {
            emit(Either.Error(CharacterErrorModel.UnknownHostError))
        }

        // When
        viewModel.getCharacterListOrderByNameAZ()

        // Then
        assertEquals(expectedCharactersUIModel, viewModel.state.value)
    }

    //ListOrderFavouritesGetCharacterUseCase

    @Test
    fun `test getCharacterListOrderByFavourites success`() {
        // Given
        val characterList = listOf(
            CharacterModel(
                id = 1,
                name = "Spider-Man",
                thumbnailDTO = "",
                description = "Un hombre que lanza telarañas"
            ),
            CharacterModel(
                id = 2,
                name = "Iron Man",
                thumbnailDTO = "",
                description = "Un hombre filántropo"
            )
        )
        val expectedCharactersUIModel = CharactersUIModel(loadingModel=true, errorScreenModel=null, characterListModel=null, headerCharacterListModel=null)

        coEvery { listOrderNameFavouritesGetCharacterUseCase.getCharacterListOrderFavourites() } returns flow { Either.Success(characterList) }

        // When
        viewModel.getCharacterListOrderByFavourites()

        // Then
        assertEquals(expectedCharactersUIModel, viewModel.state.value)
    }

    @Test
    fun `test getCharacterListOrderByFavourites error`() {
        // Given
        val expectedCharactersUIModel = CharactersUIModel(
            loadingModel = false,
            characterListModel = null,
            errorScreenModel = ErrorScreenModel(
                image = R.drawable.deadpool_no_connection,
                message = R.string.unknownError.toString()
            ),
            headerCharacterListModel = null
        )

        coEvery { listOrderNameFavouritesGetCharacterUseCase.getCharacterListOrderFavourites() } returns flow {
            emit(Either.Error(CharacterErrorModel.UnknownHostError))
        }

        // When
        viewModel.getCharacterListOrderByFavourites()

        // Then
        assertEquals(expectedCharactersUIModel, viewModel.state.value)
    }
}