package com.livingTechUSA.thatsnewstome.model.source

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Source (
    @SerializedName("id")
    val id: String = "",

    @SerializedName("Name")
    val Name: String = "null",
        ) : Parcelable {
}