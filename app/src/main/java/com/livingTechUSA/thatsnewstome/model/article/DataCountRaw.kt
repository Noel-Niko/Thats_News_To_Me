package com.livingTechUSA.thatsnewstome.model.article
import com.google.gson.annotations.SerializedName

data class DataCountRaw (
    @SerializedName("itemCount")
    val itemCount: Int = 0,

    @SerializedName("pageLength")
    val pageLength: Int = 0,

    @SerializedName("currentPage")
    val currentPage: Int = 0,

    @SerializedName("pageCount")
    val pageCount: Int = 0
)