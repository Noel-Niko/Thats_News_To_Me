package com.livingTechUSA.thatsnewstome.com.livingTechUSA.thatsnewstome.service.serializers

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.livingTechUSA.thatsnewstome.com.livingTechUSA.thatsnewstome.model.article.PagedArticlesRaw
import com.livingTechUSA.thatsnewstome.model.article.DataCountRaw
import java.lang.reflect.Type

class PagedArticlesDeserializer : JsonDeserializer<PagedArticlesRaw> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): PagedArticlesRaw {
        val gson = GsonBuilder().create()
        val dataCountRaw = gson.fromJson(json, DataCountRaw::class.java)
        val articlesRaw = gson.fromJson(json, PagedArticlesRaw::class.java)

        return articlesRaw.copy(
            dataCountRaw = dataCountRaw
        )
    }

}