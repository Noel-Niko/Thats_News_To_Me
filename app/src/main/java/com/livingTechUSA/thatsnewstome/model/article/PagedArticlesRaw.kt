package com.livingTechUSA.thatsnewstome.com.livingTechUSA.thatsnewstome.model.article

import com.google.gson.annotations.SerializedName
import com.livingTechUSA.thatsnewstome.model.article.DataCountRaw

data class PagedArticlesRaw (
    val dataCountRaw: DataCountRaw,

    @SerializedName("articles")
    val items: List<ArticleRaw>
)
