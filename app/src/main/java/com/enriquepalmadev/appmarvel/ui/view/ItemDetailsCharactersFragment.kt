package com.enriquepalmadev.appmarvel.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.enriquepalmadev.appmarvel.databinding.FragmentItemDetailsCharactersBinding
import com.enriquepalmadev.appmarvel.domain.model.Character
import com.enriquepalmadev.appmarvel.ui.view.utils.loadImage


class ItemDetailsCharactersFragment : Fragment() {
    private lateinit var  binding: FragmentItemDetailsCharactersBinding
    private var character: Character?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentItemDetailsCharactersBinding.inflate(inflater)

        retrieveCharacter()
        rederUi()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun retrieveCharacter(){
        val data: Bundle? = arguments
        character = data?.getSerializable("objectCharacter") as Character?
    }

    private fun rederUi(){
        binding.tvTitulo.text = character?.nombre
        binding.tvDescripcion.text = character?.descripcion
        character?.let { binding.ivItemDetailCharacter.loadImage(it.image) }
    }

}