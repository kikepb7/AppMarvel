package com.enriquepalmadev.appmarvel.data.featureComics.repository

import com.enriquepalmadev.appmarvel.data.featureComics.datasource.ComicRemoteDataSource
import com.enriquepalmadev.appmarvel.data.featureComics.utils.dtoToComicListModel
import com.enriquepalmadev.appmarvel.data.featureComics.utils.dtoToComicModel
import com.enriquepalmadev.appmarvel.domain.featureComics.model.ComicModel
import com.enriquepalmadev.appmarvel.domain.featureComics.ComicRepository

class ComicRepositoryImpl() : ComicRepository {

    private val remoteDataSource = ComicRemoteDataSource()

    override suspend fun fetchComicList(): List<ComicModel>? {
        return remoteDataSource.fetchComicsFromApi().data?.results?.dtoToComicListModel()
    }

    override suspend fun fetchComicDetail(comicId : Int): ComicModel? {
        return remoteDataSource.fetchComicDetailFromApi(comicId).data?.results?.getOrNull(0)?.dtoToComicModel()
    }
}