package com.livingTechUSA.thatsnewstome.com.livingTechUSA.thatsnewstome.service.api


import com.livingTechUSA.thatsnewstome.com.livingTechUSA.thatsnewstome.service.WebServices.CallNewsApi
import org.koin.core.component.KoinComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsWebServiceProvider: NewsWebServices, KoinComponent {

    var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://newsapi.org")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    override fun provideArticleWebService(): CallNewsApi {
        var builder = retrofit


        return builder
            .create(CallNewsApi::class.java)
    }
}