package com.livingTechUSA.thatsnewstome.com.livingTechUSA.thatsnewstome.database.localService

import com.livingTechUSA.thatsnewstome.com.livingTechUSA.thatsnewstome.database.dao.ArticleDao
import com.livingTechUSA.thatsnewstome.com.livingTechUSA.thatsnewstome.database.entity.ArticleEntity
import com.livingTechUSA.thatsnewstome.com.livingTechUSA.thatsnewstome.model.article.toEntity
import com.livingTechUSA.thatsnewstome.com.livingTechUSA.thatsnewstome.model.article.toModel
import com.livingTechUSA.thatsnewstome.model.article.Article


class LocalServiceProvider(
    private val articleDao: ArticleDao
) : ILocalService {
    override suspend fun insertArticle(article: Article) {
        val DB = article.toEntity()
        articleDao.insertArticle(DB)
    }

    override suspend fun removeArticle(article: Article) {
        val DB = article.toEntity()
        if (DB.title != null) {
            articleDao.removeArticle(DB.uniqueId, DB.title!!)
        }
    }

    override suspend fun clearArticleTable() {
        articleDao.clearArticleTable()
    }

    override suspend fun getAllFromArticleTable(): List<Article> {
        val DB =  articleDao.getAllFromArticleTable()
        val model = mutableListOf<Article>()
        for(it in DB){
            model.add(it.toModel())
        }
        return model
    }

    override suspend fun getOneFromArticleTable(): ArticleEntity? {
       return articleDao.getOneFromArticleTable()
    }
}