package com.livingTechUSA.thatsnewstome.com.livingTechUSA.thatsnewstome.screens.ItemDetail

import android.content.Context
import com.livingTechUSA.thatsnewstome.model.article.Article
import com.livingTechUSA.thatsnewstome.service.coroutines.IAppDispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.coroutines.CoroutineContext

class ItemDetailModel : CoroutineScope, KoinComponent {
    private val mContext: Context by inject()
    private val appDispatchers: IAppDispatchers by inject()
    private lateinit var article: Article

    private val job: Job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = appDispatchers.ui() + job

    fun setArticle(newArticle: Article) {
        article = newArticle
    }

    fun getArticle(): Article {
        return article
    }


}