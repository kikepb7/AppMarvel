package com.enriquepalmadev.appmarvel.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.enriquepalmadev.appmarvel.R
import com.enriquepalmadev.appmarvel.databinding.FragmentCharactersBinding
import com.enriquepalmadev.appmarvel.model.Character
import com.enriquepalmadev.appmarvel.view.adapter.CharactersAdapter
import com.enriquepalmadev.appmarvel.view.CharacterDetailFragment.Companion.KEY_ID
import com.enriquepalmadev.appmarvel.viewmodel.CharactersViewModel
import com.enriquepalmadev.appmarvel.viewmodel.CharactersViewModel.State
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class CharactersFragment : Fragment(), OnClickListener {
    lateinit var binding: FragmentCharactersBinding
    private val viewModel : CharactersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharactersBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
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
                    delay(3000)
                    hideLoader()
                    initRecyclerView(state.listCharacters)
                }
                State.Loading -> showLoader()
                is State.NavigateToDetail -> {
                    hideLoader()
                    navigateToCharacterDetail(state.characterId)
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun hideLoader(){
        binding.progressBar.visibility = View.GONE
    }

    private fun showLoader(){
        binding.progressBar.visibility = View.VISIBLE
    }
    private fun navigateToCharacterDetail(characterId : Long){

        binding.apply {
            rvCharacters.findNavController().navigate(R.id.action_charactersFragment_to_characterDetail)
        }
    }

    private fun initRecyclerView(list : List<Character>){
        binding.rvCharacters.apply {
            val manager = LinearLayoutManager(this.context)

            layoutManager = manager

            adapter = CharactersAdapter(list){character ->
                viewModel.onItemSelected(character.id)
            }
        }
    }

    override fun onClick(v: View?) {
        if(){

        }
    }
}