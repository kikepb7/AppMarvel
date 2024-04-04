package com.enriquepalmadev.appmarvel.data.repository

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class EjemploRepository {

    val counter: Flow<Int> = flow {
        var bombitas = 1
        while (true){
            emit(bombitas)
            delay(1000)
        }
    }
}