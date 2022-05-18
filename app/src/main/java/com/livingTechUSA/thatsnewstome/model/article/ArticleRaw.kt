package com.livingTechUSA.thatsnewstome.com.livingTechUSA.thatsnewstome.model.article

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.livingTechUSA.thatsnewstome.model.source.Source
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ArticleRaw(
    @SerializedName("source")
    val source: Source = Source(),
    @SerializedName("author")
    val author: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("url")
    val url: String = "",
    @SerializedName("urlToImage")
    val urlToImage: String = "",
    @SerializedName("publishedAt")
    val publishedAt: String = "",
    @SerializedName("content")
    val content: String = "",
    @SerializedName("uniqueId")
    val uniqueId : Long? = 0L
) : Parcelable {}
