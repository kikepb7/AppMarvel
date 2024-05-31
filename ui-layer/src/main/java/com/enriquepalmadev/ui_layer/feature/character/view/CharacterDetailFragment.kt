package com.enriquepalmadev.ui_layer.feature.character.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.enriquepalmadev.domain_layer.feature.character.model.CharacterModel
import com.enriquepalmadev.domain_layer.feature.character.utils.CharacterErrorModel
import com.enriquepalmadev.ui_layer.R
import com.enriquepalmadev.ui_layer.databinding.FragmentItemDetailsCharactersBinding
import com.enriquepalmadev.ui_layer.commons.loadImage
import com.enriquepalmadev.ui_layer.feature.character.viewmodel.CharactersDetailViewModel
import com.enriquepalmadev.ui_layer.feature.character.viewmodel.CharactersDetailViewModel.DetailState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
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
                is DetailState.Loading -> {
                    hideError()
                    showLoader()
                }

                is DetailState.CharacterError -> {
                    hideLoader()
                    showErrorCharacterError(state.error)
                }

                is DetailState.Error -> {
                    val message = getString(R.string.unknownError)
                    hideLoader()
                    showError(message)
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

    private fun showErrorCharacterError(characterErrorModel: CharacterErrorModel){
        binding.errorText.text = characterErrorModel.toString()
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