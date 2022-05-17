package com.livingTechUSA.thatsnewstome.model.article

import android.os.Parcelable
import com.livingTechUSA.thatsnewstome.model.source.Source
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Article(
    val uniqueID: String? = "",
   // val source: Source,
    val author: String? = null,
    val title: String? = null,
    val description: String? = null,
    val url: String = "",
    val urlToImage: String = "",
    val publishedAt: String = "",
    val content: String = "",
): Parcelable {}
