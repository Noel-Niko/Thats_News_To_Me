package com.livingTechUSA.thatsnewstome.Base

import com.livingTechUSA.thatsnewstome.model.article.Article

interface IPresenter {
    fun onCreated()
    fun onStart()
    fun onResume()
    fun onPause()
    fun onStop()
    fun onDestroy()
    fun onArticleSelected(article: Article)
}