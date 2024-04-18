package com.enriquepalmadev.appmarvel.domain.featureComics.usecase

import com.enriquepalmadev.appmarvel.data.featureComics.repository.ComicRepositoryImpl
import com.enriquepalmadev.appmarvel.domain.featureComics.model.ComicModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FetchComicsFilteredByNameUseCase {

    private val comicListRepository = ComicRepositoryImpl()

    suspend fun fetchComicsFilteredByName(text: String) : Flow<List<ComicModel>?> {

        return flow { emit(comicListRepository.fetchComicsFilteredByName(text)?.filter { comic ->
            (!comic.description.isNullOrEmpty() && comic.description != "#N/A") &&
                    (comic.thumbnail != "http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg")
        })}
    }
}