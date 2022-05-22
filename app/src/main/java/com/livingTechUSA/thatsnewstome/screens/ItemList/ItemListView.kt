package com.livingTechUSA.thatsnewstome.screens.ItemList

import com.livingTechUSA.thatsnewstome.model.article.Article
import com.livingTechUSA.thatsnewstome.service.api.NewsApiResponse
import retrofit2.Call

interface ItemListView {

    fun showNoArticlesFound(show: Boolean)
    fun navigateToArticleDetail(article: Article)
    fun showRecyclerViewLoader()
    fun hideRecyclerViewLoader()
//    fun showSearchViewEndDrawable(show: Boolean)
//    fun clearSearchTextIfAny()
//    fun setSearchQueryTextListener()
//    fun showErrorMessage(show: Boolean, message: String)
    suspend fun updateList(articleList: List<Article>)
    fun initPresenter(): ItemListPresenter
    suspend fun showNews(newsHeadlines: List<Article>)
}