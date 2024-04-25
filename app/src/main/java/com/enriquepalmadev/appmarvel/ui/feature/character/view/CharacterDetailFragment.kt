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


class CharacterDetailFragment : Fragment() {
    private lateinit var  binding: FragmentItemDetailsCharactersBinding
    private val viewModel: CharactersDetailViewModel by viewModels()
    private val args: CharacterDetailFragmentArgs by navArgs()

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
        viewModel.getCharacterDetail(args.id)

        binding.backButton.setOnClickListener {
            findNavController().navigate(
                CharacterDetailFragmentDirections.actionItemDetailsCharactersFragmentToCharactersFragment()
            )
        }
    }

    private fun initObserver(){
        viewModel.state.onEach{ state ->
            when(state){
                is DetailState.CharacterDetail -> {
                    hideError()
                    hideLoader()
                    state.character?.let {
                        showCharacterDetail(it)
                    }
                }
                is DetailState.Error -> {
                    hideLoader()
                    showError(state.error)
                }
                is DetailState.Loading -> {
                    hideError()
                    showLoader()
                }
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
    private fun showError(error: String){
        binding.errorText.text = error
        binding.errorText.visibility = View.VISIBLE
    }

    private fun hideError(){
        binding.errorText.visibility = View.GONE
    }

    private fun hideLoader(){
        binding.progressBarDetail.visibility = View.GONE
    }

    private fun showLoader(){
        binding.progressBarDetail.visibility = View.VISIBLE
    }

}