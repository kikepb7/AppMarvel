package com.enriquepalmadev.appmarvel.ui.feature.character.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.enriquepalmadev.appmarvel.databinding.FragmentItemDetailsCharactersBinding
import com.enriquepalmadev.appmarvel.domain.feature.character.model.CharacterModel
import com.enriquepalmadev.appmarvel.ui.feature.character.view.utils.loadImage
import com.enriquepalmadev.appmarvel.ui.feature.character.viewmodel.CharactersDetailViewModel
import com.enriquepalmadev.appmarvel.ui.feature.character.viewmodel.CharactersDetailViewModel.DetailState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class ItemDetailsCharactersFragment : Fragment() {
    private lateinit var  binding: FragmentItemDetailsCharactersBinding
    private val viewModel: CharactersDetailViewModel by viewModels()
    private val args: ItemDetailsCharactersFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentItemDetailsCharactersBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserver()
        args.let {
            viewModel.getCharacterDetail(it.id)
        }
        binding.backButton.setOnClickListener {
            findNavController().navigate(
                ItemDetailsCharactersFragmentDirections.actionItemDetailsCharactersFragmentToCharactersFragment()
            )
        }
    }

    private fun initObserver(){
        viewModel.state.onEach{ state ->
            when(state){
                is DetailState.CharacterDetail -> {
                    hideLoader()
                    state.character?.let {
                        showCharacterDetail(it)
                    }
                }
                is DetailState.Error -> {hideLoader()}
                DetailState.Loading -> {showLoader()}
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun showCharacterDetail(characterModel: CharacterModel){
        binding.apply {
            tvTitulo.text = characterModel?.name
            ivItemDetailCharacter.loadImage(characterModel.thumbnailDTO)
            tvDescripcion.text = characterModel?.description
        }
    }

    private fun hideLoader(){
        binding.progressBarDetail.visibility = View.GONE
    }

    private fun showLoader(){
        binding.progressBarDetail.visibility = View.VISIBLE
    }

}