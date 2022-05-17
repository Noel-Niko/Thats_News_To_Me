package com.livingTechUSA.thatsnewstome.com.livingTechUSA.thatsnewstome.service.WebServices

import retrofit2.http.GET
import com.livingTechUSA.thatsnewstome.service.api.NewsApiResponse
import retrofit2.Call
import retrofit2.http.Query

interface CallNewsApi {
    @GET("/v2/top-headlines")
    suspend fun callHeadlines(
        @Query("country") country: String?,
        @Query("category") category: String?,
        @Query("q") query: String?,
        @Query("apiKey") api_key: String?
    ): NewsApiResponse?
}