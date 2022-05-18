package com.livingTechUSA.thatsnewstome.com.livingTechUSA.thatsnewstome.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.livingTechUSA.thatsnewstome.com.livingTechUSA.thatsnewstome.database.entity.ArticleEntity
import com.livingTechUSA.thatsnewstome.model.article.Article
@Dao
interface ArticleDao {

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertArticle(article: ArticleEntity)


        @Query("DELETE FROM articleTable WHERE uniqueId = :id OR title =:articleName")
        suspend fun removeArticle(id: Long, articleName: String)

        @Query("SELECT * FROM articleTable ")
        suspend fun getAllFromArticleTable(): List<ArticleEntity>

        @Query("SELECT * FROM articleTable LIMIT 1")
        suspend fun getOneFromArticleTable(): ArticleEntity

        @Query("DELETE FROM articleTable ")
        suspend fun clearArticleTable()
    }

