package com.livingTechUSA.thatsnewstome.util

import androidx.recyclerview.widget.DiffUtil
import com.livingTechUSA.thatsnewstome.model.article.Article

class ArticleListDiffUtil(
    private val oldArticleList: List<Article>,
    private val newArticleList: List<Article>
) {
    //Only updates changes rather than redrawing the entire list.
    open class PatientListDiffCallback(
        private val oldArticleList: List<Article>,
        private val newArticleList: List<Article>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldArticleList.size
        }

        override fun getNewListSize(): Int {
            return newArticleList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldArticleList[oldItemPosition].title == newArticleList[newItemPosition].title
        }

        //Called if areItemsTheSame == true
        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
             return oldArticleList[oldItemPosition].title == newArticleList[newItemPosition].title
        }
    }


}