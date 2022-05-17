package com.livingTechUSA.thatsnewstome.Base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var mCurrentPosition: Int = 0

    open fun onBind(position: Int) {
        mCurrentPosition = position
    }

    fun getCurrentPosition(): Int = mCurrentPosition
}