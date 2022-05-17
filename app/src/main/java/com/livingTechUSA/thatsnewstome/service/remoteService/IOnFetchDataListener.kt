package com.livingTechUSA.thatsnewstome.service.remoteService

import com.livingTechUSA.thatsnewstome.model.article.Article

interface IOnFetchDataListener<NewsApiResponse> {
    fun onFetchData(list: List<Article?>?, message: String?)
    fun onError(message: String?)
}