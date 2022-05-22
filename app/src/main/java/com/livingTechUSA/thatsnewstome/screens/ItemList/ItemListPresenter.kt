package com.livingTechUSA.thatsnewstome.screens.ItemList

import com.livingTechUSA.thatsnewstome.Base.BasePresenter
import com.livingTechUSA.thatsnewstome.model.article.Article
import com.livingTechUSA.thatsnewstome.service.api.NewsApiResponse
import com.livingTechUSA.thatsnewstome.service.coroutines.IAppDispatchers
import kotlinx.coroutines.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.coroutines.CoroutineContext
import com.livingTechUSA.thatsnewstome.com.livingTechUSA.thatsnewstome.model.article.toNewModel


class ItemListPresenter(
    private val mView: ItemListView,
    private val mModel: ItemListModel
): BasePresenter(), CoroutineScope, KoinComponent {

    private val appDispatchers: IAppDispatchers by inject()

    private val job: Job = SupervisorJob()
    override val coroutineContext: CoroutineContext
    get() = job + appDispatchers.ui()
    private val BEGIN_SEARCH_AFTER_MILLIS = 500L

    private var update = false
    private var country: String = "us"
    private var category: String = "general"
    private var searchQuery: String = ""

    override fun onCreated() {
        super.onCreated()
        launch(appDispatchers.io()) {
            initHeadlineList()?.articles?.let {
                val list = mutableListOf<Article>()
                for(each in it)(
                        list.add(each.toNewModel())
                )
                mModel.addArticles(
                    list) }
            val articleList = mModel.getArticles()
            mView.showNews(articleList)
          //  mView.setSearchQueryTextListener()
        }
    }

        override fun onResume() {
            super.onResume()
            mView.showRecyclerViewLoader()
            changeSearchQueryListenerState(true)
        }

//    private var searchJob: Job? = null
//
//    fun search(query: String) {
//        searchJob?.cancel()
//        searchJob = launch {
//            delay(BEGIN_SEARCH_AFTER_MILLIS)
//            mModel.changeSearchQuery(query)
//            mView.showRecyclerViewLoader()
//            launch(appDispatchers.ui()){
//                mView.showLoading(true)
//            }
//            mModel.clearArticles()
//            launch {
//                getArticles()
//            }
//        }
//    }
//
//    private suspend fun getArticles() {
//        if(update){
//            mModel.changeCountry(country)
//            mModel.changeCategory(category)
//            mModel.changeSearchQuery(searchQuery)
//        }
//        val call = mModel.getNewsHeadlines()
//        try {
//            call?.articles?.let {
//                val list = mutableListOf<Article>()
//                for(each in it)(
//                        list.add(each.toNewModel())
//                        )
//                updateList(list) }
//        } catch (e:Exception){
//           e.printStackTrace()
//            mView.showErrorMessage(true, "Error attempting to obtain news.")
//        }
//    }
//
//    fun updateList(articleList: List<Article>) {
//        mModel.addArticles(articleList)
//        launch(appDispatchers.io()) {  mView.updateList(articleList) }
//
//    }
//
//    fun clearSearchText() {
//        mModel.clearSearchQuery()
//    }
//    fun onSearchQueryChange(
//        query: String,
//    ) {
//        if (isSearchQueryListenerEnable()) {
//            val showEndDrawable = query.isNotEmpty()
//            mView.showSearchViewEndDrawable(showEndDrawable)
//            search(query)
//        }
//    }

    private fun isSearchQueryListenerEnable(): Boolean = mModel.isSearchQueryListenerEnable()

    private fun changeSearchQueryListenerState(enable: Boolean) {
        mModel.changeSearchQueryListenerState(enable)
    }

    fun isSearchSelected(): Boolean = mModel.isSearchSelected()

    fun setSearchSelected(isSearchSelected: Boolean) {
        mModel.setSearchSelected(isSearchSelected)
    }

    suspend fun initHeadlineList(): NewsApiResponse? {
       return  mModel.getNewsHeadlines()
    }

    override fun onArticleSelected(article: Article) {
        mModel.setIsArticleSelected(true)
        mView.navigateToArticleDetail(article)
    }


}