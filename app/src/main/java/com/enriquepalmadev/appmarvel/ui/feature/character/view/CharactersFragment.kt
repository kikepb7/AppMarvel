package com.enriquepalmadev.appmarvel.ui.feature.character.view

import android.os.Bundle
import android.util.Log
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
import com.enriquepalmadev.appmarvel.R
import com.enriquepalmadev.appmarvel.databinding.FragmentCharactersBinding
import com.enriquepalmadev.appmarvel.domain.feature.character.model.CharacterModel
import com.enriquepalmadev.appmarvel.ui.feature.character.view.adapter.CharactersAdapter
import com.enriquepalmadev.appmarvel.ui.feature.character.viewmodel.CharactersViewModel
import com.enriquepalmadev.appmarvel.ui.feature.character.viewmodel.CharactersViewModel.State
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class CharactersFragment : Fragment() {
    private lateinit var binding: FragmentCharactersBinding
    private lateinit var charactersAdapter: CharactersAdapter
    private val viewModel : CharactersViewModel by viewModels()
    private var stateJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharactersBinding.inflate(inflater)
        val view = binding.root
        viewModel.getCharacterList()//Forzamos la carga de la lista para garantizar q se carga al crear el fragmento.
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        stateJob?.cancel()//Cancelamos la posible tarea que se este ejecutando.
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
        stateJob = viewModel.state.onEach{ state ->
            when(state){
                is State.Error -> {
                    hideLoader()
                    //Accedemos a la variable state desde el Either del Viewmodel
                    //Hacemos metodos para mostrar el Error mediante un texto.
                    showError(state.error)
                }
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
                is State.NavigateToDetail -> navigateToCharacterDetail(state.characterId)
                else -> {}
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
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
                viewModel.onItemSelected(c)
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
                    Log.d("BOTON FILTER", "opcion ordenar por name")
                    viewModel.getCharacterListOrderByName()
                    true
                }
                R.id.favoritos ->{
                    Log.d("BOTON FILTER", "opcion favoritos")
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }
}