package com.livingTechUSA.thatsnewstome.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import com.axxess.palliative.RoomTestUtil
import com.livingTechUSA.thatsnewstome.com.livingTechUSA.thatsnewstome.database.NewsDatabase
import com.livingTechUSA.thatsnewstome.com.livingTechUSA.thatsnewstome.database.dao.ArticleDao
import com.livingTechUSA.thatsnewstome.com.livingTechUSA.thatsnewstome.database.entity.ArticleEntity
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NewsDatabaseTest {
    private lateinit var db: NewsDatabase
    private lateinit var articleDoa: ArticleDao
    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, NewsDatabase::class.java).build()
        articleDoa = db.articleDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun testWriteAndReadArticleAntity() = runBlocking {
        //GIVEN
        val article: ArticleEntity = RoomTestUtil.createArticleEntity().apply {
            uniqueId = 0L
            id = "id"
            name = "a name"
            author = "R. Ellison"
            title = "New News"
            description = "Lots to know"
            url = "url..."
            urlToImage = "https://..."
            publishedAt = "1-1-01"
            content = "lorem ipsum..."
        }
        articleDoa.insertArticle(article)
        //WHEN
        val aA = articleDoa.getOneFromArticleTable()
        //THEN
        assertEquals(aA.publishedAt, article.publishedAt)
    }


}