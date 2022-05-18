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
    var uniqueId: Long = 0L,
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("author")
    var author: String? = null,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("description")
    var description: String? = null,
    @SerializedName("url")
    var url: String? = "",
    @SerializedName("urlToImage")
    var urlToImage: String? = "",
    @SerializedName("publishedAt")
    var publishedAt: String? = "",
    @SerializedName("content")
    var content: String? = "") {

}