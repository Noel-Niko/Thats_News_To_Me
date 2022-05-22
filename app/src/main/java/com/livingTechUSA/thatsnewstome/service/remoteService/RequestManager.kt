package com.livingTechUSA.thatsnewstome.service.remoteService

import android.content.Context
import android.widget.Toast
import com.example.thatsnewstome.R
import com.livingTechUSA.thatsnewstome.com.livingTechUSA.thatsnewstome.service.WebServices.CallNewsApi
import com.livingTechUSA.thatsnewstome.com.livingTechUSA.thatsnewstome.service.api.NewsWebServices
import com.livingTechUSA.thatsnewstome.service.api.NewsApiResponse
import com.livingTechUSA.thatsnewstome.service.coroutines.IAppDispatchers
import kotlinx.coroutines.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.await
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class RequestManager(var context: Context): CallNewsApi, CoroutineScope, KoinComponent {
    private val newsWebServices: NewsWebServices by inject()
    private val appDispatchers: IAppDispatchers by inject()

    private val job: Job = SupervisorJob()
    override val coroutineContext: CoroutineContext
    get() = job + appDispatchers.ui()


    suspend fun getNewsHeadlines(
        country: String,
        category: String,
        query: String
    ): NewsApiResponse? {
        try {
           val _result = coroutineScope {
               async {
                   newsWebServices.provideArticleWebService()
                       .callHeadlines(country, category, query, context.getString(R.string.api_key))
               }
           }
           val result = _result.await()
           return result

        } catch (e: Exception) {
            coroutineScope {
                launch(appDispatchers.ui()) {
                    Toast.makeText(
                        context, "Unable to download articles." +
                                "\n" +
                                "Do you have and internet connection?", Toast.LENGTH_SHORT
                    ).show()
                }
            }
            e.printStackTrace()
        }
        return null
    }


    override suspend fun callHeadlines(
        country: String?,
        category: String?,
        query: String?,
        api_key: String?
    ): NewsApiResponse? {
        return callHeadlines(country,category, query, api_key)
    }
}

