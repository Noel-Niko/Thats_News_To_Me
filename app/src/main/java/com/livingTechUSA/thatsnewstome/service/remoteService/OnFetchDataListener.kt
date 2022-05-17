package com.livingTechUSA.thatsnewstome.service.remoteService

import com.livingTechUSA.thatsnewstome.model.article.Article
import com.livingTechUSA.thatsnewstome.service.api.NewsApiResponse

class OnFetchDataListener: IOnFetchDataListener<NewsApiResponse> {
    override fun onFetchData(list: List<Article?>?, message: String?) {
        TODO("Not yet implemented")
    }

    override fun onError(message: String?) {
        TODO("Not yet implemented")
    }
}