package com.livingTechUSA.thatsnewstome.com.livingTechUSA.thatsnewstome.screens.SavedList

import android.content.Context
import com.livingTechUSA.thatsnewstome.com.livingTechUSA.thatsnewstome.database.localService.ILocalService
import com.livingTechUSA.thatsnewstome.model.article.Article
import com.livingTechUSA.thatsnewstome.service.api.NewsApiResponse
import com.livingTechUSA.thatsnewstome.service.coroutines.IAppDispatchers
import com.livingTechUSA.thatsnewstome.service.remoteService.RequestManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.coroutines.CoroutineContext

class SavedArticlesModel : CoroutineScope, KoinComponent {
    private val mContext: Context by inject()
    private val appDispatchers: IAppDispatchers by inject()
    private val localServiceProvider:ILocalService by inject()
    private val articles: MutableList<Article> = mutableListOf()

    private val job: Job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = appDispatchers.ui() + job

    private var country: String = "us"
    private var category: String = "general"
    private var mSearchQuery: String = ""
    private var mIsSearchSelected = false
    private var mIsSearchQueryListenerEnable = false
    private var mIsArticleSelected = false

    val manager: RequestManager = RequestManager(mContext)

    suspend fun getNewsHeadlines(): List<Article> = localServiceProvider.getAllFromArticleTable()


    fun changeSearchQuery(query: String) {
        mSearchQuery = query
    }

    fun getSearchQuery(): String = mSearchQuery

    fun clearSearchQuery() {
        mSearchQuery = ""
    }

    fun changeCountry(newCountry: String) {
        country = newCountry
    }

    fun getCountry(): String = country

    fun clearCountryQuery() {
        country = ""
    }

    fun changeCategory(newCategory: String) {
        mSearchQuery = newCategory
    }

    fun getCategory(): String = category

    fun clearCategory() {
        category = ""
    }

    fun addArticles(articlesList: List<Article>) = articles.addAll(articlesList)

    fun getArticles(): List<Article> = articles.toList()

    fun clearArticles() {
        articles.clear()
    }

    fun isSearchSelected(): Boolean = mIsSearchSelected

    fun setSearchSelected(isSearchSelected: Boolean) {
        mIsSearchSelected = isSearchSelected
    }

    fun isSearchQueryListenerEnable(): Boolean = mIsSearchQueryListenerEnable

    fun changeSearchQueryListenerState(enable: Boolean) {
        mIsSearchQueryListenerEnable = enable
    }


    fun isArticletSelected(): Boolean = mIsArticleSelected

    fun setIsArticleSelected(isSelected: Boolean) {
        mIsArticleSelected = isSelected
    }


}