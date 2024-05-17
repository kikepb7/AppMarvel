package com.enriquepalmadev.ui_layer.feature.character.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupMenu
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.enriquepalmadev.domain_layer.feature.character.model.CharacterModel
import com.enriquepalmadev.domain_layer.feature.character.utils.CharacterErrorModel
import com.enriquepalmadev.ui_layer.R
import com.enriquepalmadev.ui_layer.databinding.FragmentCharactersBinding
import com.enriquepalmadev.ui_layer.feature.character.view.adapter.CharactersAdapter
import com.enriquepalmadev.ui_layer.feature.character.viewmodel.CharactersViewModel
import com.enriquepalmadev.ui_layer.feature.character.viewmodel.CharactersViewModel.State
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

// TODO Diferencia entre State Flow y Shared Flow

@AndroidEntryPoint
class CharactersFragment : Fragment() {
    private lateinit var binding: FragmentCharactersBinding
    private lateinit var charactersAdapter: CharactersAdapter
    private val viewModel : CharactersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharactersBinding.inflate(inflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Initializes the observer in case the screen state changes
        initObserver()

        //Call the viewModel to bring us the list of characters
        viewModel.getCharacterList()

        //Filter function
        binding.btFiltros.setOnClickListener {
            showFiltersMenu(binding.btFiltros)
        }

        //Search Function
        binding.svBuscador.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            //When the customer submit the text
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.getCharacterFiltList(query.orEmpty())
                return true
            }

            //When the text have been changed
            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.getCharacterFiltList(newText.orEmpty())
                return false
            }

        })
    }

    private fun initObserver(){
        viewModel.state.onEach{ state ->
            when(state){
                is State.ListReceived -> {
                    hideError()
                    hideLoader()
                    //Funcion a lista recibida.
                    state.listCharacters?.let {
                        initRecyclerView(it)
                    }
                }
                is State.Loading -> {
                    showLoader()
                }
                is State.CharacterError -> {
                    hideLoader()
                    showErrorCharacterError(state.error)

                }
                is State.Error -> {
                    val message = getString(R.string.unknownError)
                    hideLoader()
                    showError(message)
                }
                is State.NavigateToDetail -> navigateToCharacterDetail(state.characterId)
                else -> {}
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun showErrorCharacterError(characterErrorModel: CharacterErrorModel){
        binding.errorText.text = characterErrorModel.toString()
        binding.errorText.visibility = View.VISIBLE
    }

    private fun showError(error: String){
        binding.errorText.text = error
        binding.errorText.visibility = View.VISIBLE
    }

    private fun hideError(){
        binding.errorText.visibility = View.GONE
    }

    private fun hideLoader(){
        binding.progressBar.visibility = View.GONE
    }

    private fun showLoader(){
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun navigateToCharacterDetail(characterId: Int){
        binding.apply {
            findNavController().navigate(
                CharactersFragmentDirections.actionCharactersFragmentToItemDetailsCharactersFragment(
                    id = characterId
                )
            )
        }
    }

    private fun initRecyclerView(list: List<CharacterModel>){

        binding.rvCharacters.apply {
            val manager = LinearLayoutManager(this.context)

            layoutManager = manager

            charactersAdapter = CharactersAdapter(list){c ->
                navigateToCharacterDetail(c)
            }
            binding.rvCharacters.adapter = charactersAdapter
        }
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