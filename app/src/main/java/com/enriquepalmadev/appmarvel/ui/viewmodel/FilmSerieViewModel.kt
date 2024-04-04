package com.enriquepalmadev.appmarvel.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enriquepalmadev.appmarvel.data.repository.EjemploRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class FilmSerieViewModel : ViewModel() {

    val ejemploRepository = EjemploRepository()

    fun example(){
        // Launching a corutine at the level of the view model
        viewModelScope.launch {
            ejemploRepository.counter
                .map { it.toString() } //numBombitas
                .collect{ bombitas: String ->
                Log.i("aristiCurso", bombitas)
            }
        }
    }
}