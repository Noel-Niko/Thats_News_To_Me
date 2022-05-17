package com.livingTechUSA.thatsnewstome.com.livingTechUSA.thatsnewstome.screens.ItemDetail

import com.livingTechUSA.thatsnewstome.model.article.Article

interface ItemDetailView {
    fun showLoading(bool: Boolean)
    suspend fun updateList(articleList: List<Article>)
    fun initPresenter(): ItemDetailPresenter
    suspend fun saveArticle(article: Article)
    suspend fun removeFromSavedArticles(article: Article)
    fun navigateToSavedArticles()
    fun showNews()
}