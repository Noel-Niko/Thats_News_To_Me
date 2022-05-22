package com.livingTechUSA.thatsnewstome.com.livingTechUSA.thatsnewstome.screens.SavedList

import com.livingTechUSA.thatsnewstome.model.article.Article
import com.livingTechUSA.thatsnewstome.screens.ItemList.ItemListPresenter

interface SavedArticlesView {
    suspend fun updateList(articleList: List<Article>)
    fun navigateToArticleDetail(article: Article)
    fun showNoArticlesFound(show: Boolean)
    suspend fun showNews(newsHeadlines: List<Article>)
    fun initPresenter(): SavedArticlesPresenter
//    fun showSearchViewEndDrawable(show: Boolean)
//    fun clearSearchTextIfAny()
//    fun setSearchQueryTextListener()
//    fun showErrorMessage(show: Boolean, message: String)
//    fun setSearchViewDrawableTouchListener()
}