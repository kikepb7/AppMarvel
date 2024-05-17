package com.enriquepalmadev.ui_layer.feature.character.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

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
           val state by viewModel.state.collectAsState()//TODO onFavClick
            CharacterListScreen(
                model = state,
                onCharacterClicked = { navigateToCharacterDetail(it.id) },
                dialogOrderBy = { showDialogOrderBy() },
                onSearchQueryChange = { newtext -> onSearchQueryChange(newtext) },
                onFavClicked = {  }
            )
        }
        viewModel.getCharacterList()
    }

    // Dialog to select the items order
    private fun showDialogOrderBy() {

        var selectedItemIndex: Int = 0
        val arrayItemsOrderBy = arrayOf(
            getString(R.string.orderby_fav_only),
            getString(R.string.orderby_alphabet_A_Z),
            getString(R.string.orderby_alphabet_Z_A)
        )
        var selectedItem = arrayItemsOrderBy[selectedItemIndex]

        context?.let { context ->
            MaterialAlertDialogBuilder(context)
                .setTitle(getString(R.string.dialog_title))
                .setSingleChoiceItems(arrayItemsOrderBy, selectedItemIndex) { _, which ->
                    selectedItemIndex = which
                    selectedItem = arrayItemsOrderBy[which]
                }
                .setPositiveButton(getString(R.string.dialog_ok)) { _, _ ->
                    orderListBy(selectedItem, requireContext())
                }
                .setNegativeButton(getString(R.string.dialog_cancel)) { _, _ ->
                    // No action needed when cancel is clicked
                }
                .show()
        }
    }

    private fun orderListBy(selectedItem: String, context: Context) {
        when (selectedItem) {
            context.getString(R.string.orderby_fav_only) -> viewModel.getCharacterListOrderByFavourites()
            context.getString(R.string.orderby_alphabet_A_Z) -> viewModel.getCharacterListOrderByNameAZ()
            context.getString(R.string.orderby_alphabet_Z_A) -> viewModel.getCharacterListOrderByNameZA()
        }
    }

    private fun onSearchQueryChange(newText: String) {
        viewModel.getCharacterFiltList(newText)
    }

    private fun navigateToCharacterDetail(characterId: Int) {

        findNavController().navigate(
            CharactersFragmentDirections.actionCharactersFragmentToItemDetailsCharactersFragment(
                id = characterId
            )
        )

    }
}