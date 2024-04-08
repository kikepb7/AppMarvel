package com.enriquepalmadev.appmarvel.ui.view

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
import com.enriquepalmadev.appmarvel.domain.model.Character
import com.enriquepalmadev.appmarvel.ui.view.adapter.CharactersAdapter
import com.enriquepalmadev.appmarvel.ui.viewmodel.CharactersViewModel
import com.enriquepalmadev.appmarvel.ui.viewmodel.CharactersViewModel.State
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


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
            Log.d("BOTON FILTRAR", "Ha sido pulsado.")
            showFiltersMenu(binding.btFiltros)
        }

        //Search Function
        binding.svBuscador.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            //When the customer submit the text
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d("BOTON BARRA BUSCAR", "Enviado")
                return false
            }

            //When the text have been changed
            override fun onQueryTextChange(newText: String?): Boolean {
                var searchText = newText?:""
                Log.d("BOTON BARRA BUSCAR", searchText)
                return true
            }

        })
    }

    private fun initObserver(){
        viewModel.state.onEach{ state ->
            when(state){
                State.Error -> hideLoader()
                is State.ListReceived -> {
                    hideLoader()
                    initRecyclerView(state.listCharacters)
                }
                State.Loading -> showLoader()
                is State.NavigateToDetail -> navigateToCharacterDetail(state.character)
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun hideLoader(){
        binding.progressBar.visibility = View.GONE
    }

    private fun showLoader(){
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun navigateToCharacterDetail(character: Character){
        val characterData = Bundle().apply {
            putSerializable("objectCharacter", character)
        }
        binding.apply {
            findNavController().navigate(R.id.action_charactersFragment_to_itemDetailsCharactersFragment, characterData)
        }
    }

    private fun initRecyclerView(list : List<Character>){

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

                    Log.d("BOTON FILTER", "opcion orden alfabetico")
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