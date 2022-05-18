package com.livingTechUSA.thatsnewstome.com.livingTechUSA.thatsnewstome.database.localService


import com.livingTechUSA.thatsnewstome.com.livingTechUSA.thatsnewstome.database.entity.ArticleEntity
import com.livingTechUSA.thatsnewstome.model.article.Article

interface ILocalService {
    suspend fun insertArticle(article: Article)
    suspend fun removeArticle(article: Article)
    suspend fun clearArticleTable()
    suspend fun getAllFromArticleTable(): List<Article>
    suspend fun getOneFromArticleTable(): ArticleEntity?
}