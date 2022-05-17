package com.livingTechUSA.thatsnewstome.model.article

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataCount (
    val pageCount: Int,
    val itemCount: Int
) : Parcelable