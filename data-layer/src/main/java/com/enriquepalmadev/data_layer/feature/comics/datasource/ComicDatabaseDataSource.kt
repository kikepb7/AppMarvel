package com.enriquepalmadev.data_layer.feature.comics.datasource

import com.enriquepalmadev.data_layer.feature.comics.database.dao.ComicDao
import com.enriquepalmadev.data_layer.feature.comics.database.entities.ComicEntity
import com.enriquepalmadev.data_layer.feature.comics.database.entities.FavoriteComicEntity
import com.enriquepalmadev.data_layer.feature.comics.utils.comicEntityToComicModel
import com.enriquepalmadev.domain_layer.feature.comics.model.ComicModel
import javax.inject.Inject

class ComicDatabaseDataSource @Inject constructor(
    private val comicDao: ComicDao
) {
    suspend fun findComicsFromDatabase(): List<ComicModel> {
        return comicDao.findAllComics().map { comicEntity ->
            comicEntity.comicEntityToComicModel()
        }
    }

    suspend fun findComicDetailFromDatabase(comicId: Int): ComicModel? {
        return comicDao.findComicById(comicId = comicId)?.comicEntityToComicModel()
    }

    suspend fun insertComicToDatabase(comic: FavoriteComicEntity) {
        return comicDao.insertComic(comic = comic)
    }
    suspend fun removeComicFromDatabase(comicId: Int) {
        return comicDao.removeComic(comicId = comicId)
    }

    suspend fun getFavoriteComics(): List<FavoriteComicEntity> {
        return comicDao.getFavoriteComicList()
    }

    suspend fun insertComicListToDatabase(comicList: List<ComicEntity>) {
        return comicDao.insertAllComics(comicList = comicList)
    }

    suspend fun clearComic(comicId: Int) {
        return comicDao.deleteComicById(comicId = comicId)
    }

    suspend fun clearComicList() {
        return comicDao.deleteAllComics()
    }
}