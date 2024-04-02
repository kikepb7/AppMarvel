package com.enriquepalmadev.appmarvel.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.enriquepalmadev.appmarvel.R
import com.enriquepalmadev.appmarvel.databinding.FragmentCharactersBinding
import com.enriquepalmadev.appmarvel.view.adapter.CharactersAdapter
import com.enriquepalmadev.appmarvel.model.CharacterProvider


class CharactersFragment : Fragment() {
    lateinit var binding: FragmentCharactersBinding

    //lateinit var adapter: CharactersAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharactersBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView(binding.rvCharacters)
    }

    private fun initRecyclerView(view: View){

        val manager = LinearLayoutManager(view.context)

        binding.rvCharacters.layoutManager = manager
        binding.rvCharacters.adapter = CharactersAdapter(CharacterProvider.characterList){character -> onItemSelected() }
    }

    private fun onItemSelected(){
        binding.apply {
            rvCharacters.findNavController().navigate(R.id.action_charactersFragment_to_characterDetail)
        }
    }
}