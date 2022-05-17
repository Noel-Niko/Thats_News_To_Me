package com.livingTechUSA.thatsnewstome.com.livingTechUSA.thatsnewstome.service.api

import com.livingTechUSA.thatsnewstome.com.livingTechUSA.thatsnewstome.service.WebServices.CallNewsApi

interface NewsWebServices {
    fun provideArticleWebService(): CallNewsApi
}