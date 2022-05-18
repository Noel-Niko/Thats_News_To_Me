package com.livingTechUSA.thatsnewstome.com.livingTechUSA.thatsnewstome.screens.ItemDetail

import com.livingTechUSA.thatsnewstome.model.article.Article

interface ItemDetailView {
    fun showLoading(bool: Boolean)
    fun initPresenter(): ItemDetailPresenter
    suspend fun navigateToSavedArticles()
    fun showNews()
}