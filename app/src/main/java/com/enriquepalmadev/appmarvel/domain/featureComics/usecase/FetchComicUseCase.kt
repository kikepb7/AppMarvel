package com.enriquepalmadev.appmarvel.domain.featureComics.usecase

import com.enriquepalmadev.appmarvel.data.featureComics.repository.ComicRepositoryImpl
import com.enriquepalmadev.appmarvel.domain.featureComics.model.ComicModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FetchComicUseCase {

    private val comicListRepository = ComicRepositoryImpl()

    suspend fun fetchComicList(): Flow<List<ComicModel>?> {

        val filteredList = comicListRepository.fetchComicList()?.filter { comic ->
            (!comic.description.isNullOrEmpty() && comic.description != "#N/A") &&
                    (comic.thumbnail != "http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg")
        }

        return flow { emit(filteredList) }
    }
}