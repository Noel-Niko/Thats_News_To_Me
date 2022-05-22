package com.livingTechUSA.thatsnewstome.com.livingTechUSA.thatsnewstome.screens.ItemDetail

import com.livingTechUSA.thatsnewstome.Base.BasePresenter
import com.livingTechUSA.thatsnewstome.model.article.Article
import com.livingTechUSA.thatsnewstome.service.coroutines.IAppDispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.coroutines.CoroutineContext

class ItemDetailPresenter(
    private val mView: ItemDetailView,
    private val mModel: ItemDetailModel
) : BasePresenter(), CoroutineScope, KoinComponent {

    private val appDispatchers: IAppDispatchers by inject()

    private val job: Job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = job + appDispatchers.ui()

    override fun onCreated() {
        super.onCreated()
        mView.showNews()
    }

    fun setArticle(newArticle: Article) {
        mModel.setArticle(newArticle)
    }

    fun getArticle(): Article {
        return mModel.getArticle()
    }

    fun setBundle(selectedArticle: Article?) {
        if(selectedArticle != null){
            mModel.setArticle(selectedArticle)
        }
    }
}
