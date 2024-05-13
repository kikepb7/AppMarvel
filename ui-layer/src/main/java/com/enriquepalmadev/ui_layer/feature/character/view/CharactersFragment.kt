package com.enriquepalmadev.ui_layer.feature.character.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupMenu
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.enriquepalmadev.domain_layer.feature.character.utils.CharacterErrorModel
import com.enriquepalmadev.ui_layer.R
import com.enriquepalmadev.ui_layer.feature.character.view.compose.CharacterListScreen
import com.enriquepalmadev.ui_layer.feature.character.viewmodel.CharactersViewModel
import com.enriquepalmadev.ui_layer.feature.character.viewmodel.CharactersViewModel.State
import dagger.hilt.android.AndroidEntryPoint

// TODO Diferencia entre State Flow y Shared Flow

@AndroidEntryPoint
class CharactersFragment : Fragment() {
    private lateinit var composeView: ComposeView
    private val viewModel: CharactersViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        return ComposeView(requireContext()).also {
            composeView = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        composeView.setContent {
            val state by viewModel.state.collectAsState()// recolecta los estados
            //Poner estados dentro del setContent
            when (state) {
                is State.CharacterError -> {
                    showErrorCharacterError((state as State.CharacterError).error)
                }

                State.Error -> {
                    val message = getString(R.string.unknownError)
                    showError(message)
                }

                is State.ListReceived -> {
                    CharacterListScreen(//pasar por lambdas
                        modifier = Modifier,
                        navController = findNavController(),
                        state = state,
                        characterList = (state as State.ListReceived).listCharacters
                    )
                }

                State.Loading -> {
                    showLoader()

                }

                is State.NavigateToDetail -> {

                    navigateToCharacterDetail((state as State.NavigateToDetail).characterId)
                }

            }
        }
    }
    @Composable
    private fun showErrorCharacterError(characterErrorModel: CharacterErrorModel) {
        Text(
            text = characterErrorModel.toString(),
            color = Color.Red,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(16.dp)
        )
    }

    @Composable
    private fun showError(errorMessage: String) {
        Text(
            text = errorMessage,
            color = Color.Red,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(16.dp)
        )
    }


    @Composable
    private fun showLoader() {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator()
        }
    }

    private fun navigateToCharacterDetail(characterId: Int) {

        findNavController().navigate(
            CharactersFragmentDirections.actionCharactersFragmentToItemDetailsCharactersFragment(
                id = characterId
            )
        )

    }

    private fun showFiltersMenu(anchorView: Button){

        val popupMenu = PopupMenu(context, anchorView)
        val inflater: MenuInflater = popupMenu.menuInflater
        inflater.inflate(R.menu.filters_menu, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId){
                R.id.ordenAlfabetico ->{
                    viewModel.getCharacterListOrderByName()
                    true
                }
                R.id.favoritos ->{
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }
}