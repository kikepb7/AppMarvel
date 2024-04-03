package com.enriquepalmadev.appmarvel.ui.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.enriquepalmadev.appmarvel.R
import com.enriquepalmadev.appmarvel.databinding.FragmentCharactersBinding
import com.enriquepalmadev.appmarvel.databinding.ItemSuperheroBinding
import com.enriquepalmadev.appmarvel.domain.model.Character
import com.enriquepalmadev.appmarvel.domain.model.CharacterProvider
import com.enriquepalmadev.appmarvel.ui.view.adapter.CharactersAdapter
import com.enriquepalmadev.appmarvel.ui.view.utils.getJsonFromAssets
import com.enriquepalmadev.appmarvel.ui.viewmodel.CharactersViewModel
import com.enriquepalmadev.appmarvel.ui.viewmodel.CharactersViewModel.State
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class CharactersFragment : Fragment() {
    private lateinit var binding: FragmentCharactersBinding
    private lateinit var charactersAdapter: CharactersAdapter
    private var copyListCharacters : ArrayList<Character> = ArrayList()
    private val viewModel : CharactersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharactersBinding.inflate(inflater)
        val view = binding.root

        /*charactersAdapter = CharactersAdapter(::navigateToCharacterDetail)
        binding.rvCharacters.adapter = charactersAdapter//Setting the Adapter in the RecyclerView*/

        //copyListCharacters = charactersAdapter.refreshList(getListFromView()) as ArrayList<Character>

        return view
    }

    //Reach the list from viewModel
    private fun getListFromView(): ArrayList<Character>{
        var lista = viewModel.getCharacterList()
        return ArrayList(lista)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Initializes the observer in case the screen state changes
        initObserver()

        //Call the viewModel to bring us the list of characters
        viewModel.getCharacterList()
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
        /*binding.apply {
            rvCharacters.findNavController().navigate(R.id.action_charactersFragment_to_characterDetail)
        }*/
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

}