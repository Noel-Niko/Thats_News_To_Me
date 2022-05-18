package com.axxess.palliative

import com.livingTechUSA.thatsnewstome.com.livingTechUSA.thatsnewstome.database.entity.ArticleEntity



object TestUtil {

    fun createArticleEntity() = ArticleEntity(
        uniqueId = 0L,
        id = "id",
        name = "a name",
        author = "R. Ellison",
        title = "New News",
        description = "Lots to know",
        url = "url...",
        urlToImage = "https://...",
        publishedAt = "1-1-01",
        content = "lorem ipsum..."
    )

}