package com.livingTechUSA.thatsnewstome.com.livingTechUSA.thatsnewstome.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.livingTechUSA.thatsnewstome.model.source.Source
import java.util.concurrent.atomic.AtomicInteger

@Entity(tableName = "articleTable")
data class ArticleEntity(
    @PrimaryKey(autoGenerate = true) @SerializedName("uniqueId")
    val uniqueId: Long = 0L,
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("author")
    val author: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("url")
    val url: String? = "",
    @SerializedName("urlToImage")
    val urlToImage: String? = "",
    @SerializedName("publishedAt")
    val publishedAt: String? = "",
    @SerializedName("content")
    val content: String? = "") {

}