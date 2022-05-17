package com.livingTechUSA.thatsnewstome.Base

import android.content.DialogInterface
import androidx.annotation.StringRes
import com.livingTechUSA.thatsnewstome.model.article.Article
import com.livingTechUSA.thatsnewstome.service.coroutines.IAppDispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.coroutines.CoroutineContext

open class BasePresenter() : IPresenter, KoinComponent, CoroutineScope {

    val appDispatcher: IAppDispatchers by inject()

    override val coroutineContext: CoroutineContext
        get() = appDispatcher.ui() + SupervisorJob()

    override fun onCreated() {
    }

    override fun onStart() {
    }

    override fun onResume() {
    }

    override fun onPause() {

    }

    override fun onStop() {
    }

    override fun onDestroy() {

    }

    override fun onArticleSelected(article: Article) {

    }

}