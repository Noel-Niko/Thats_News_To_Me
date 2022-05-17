package com.livingTechUSA.thatsnewstome.screens.ItemDetail

import android.content.ClipData
import android.os.Bundle
import android.view.DragEvent
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.CollapsingToolbarLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.thatsnewstome.R
import com.example.thatsnewstome.databinding.FragmentItemDetailBinding
import com.livingTechUSA.thatsnewstome.com.livingTechUSA.thatsnewstome.screens.ItemDetail.ItemDetailModel
import com.livingTechUSA.thatsnewstome.com.livingTechUSA.thatsnewstome.screens.ItemDetail.ItemDetailPresenter
import com.livingTechUSA.thatsnewstome.com.livingTechUSA.thatsnewstome.screens.ItemDetail.ItemDetailView
import com.livingTechUSA.thatsnewstome.model.article.Article
import com.livingTechUSA.thatsnewstome.service.coroutines.IAppDispatchers
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.coroutines.CoroutineContext


class ItemDetailFragment() : Fragment(), ItemDetailView, CoroutineScope, KoinComponent {
    val appDispatcher: IAppDispatchers by inject()
    companion object {
        fun getNewInstance(bundle: Bundle?): ItemDetailFragment {
            val fragment = ItemDetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private val job: Job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = appDispatcher.io() + job


    override suspend fun saveArticle(article: Article) {
        TODO("Not yet implemented")
    }

    override suspend fun removeFromSavedArticles(article: Article) {
        TODO("Not yet implemented")
    }

    override fun navigateToSavedArticles() {
        TODO("Not yet implemented")
    }


    private lateinit var presenter: ItemDetailPresenter
    private val nLoading: View? by lazy { inflateLoaderLayout() }
    open fun inflateLoaderLayout(): View? = null

    lateinit var itemDetailTextView: TextView
    private var toolbarLayout: CollapsingToolbarLayout? = null

    private var _binding: FragmentItemDetailBinding? = null

    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initPresenter()
        _binding = FragmentItemDetailBinding.inflate(inflater, container, false)
        val rootView = binding.root
        return rootView
    }

    val args: ItemDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.setBundle(args.article)
        presenter.onCreated()

        binding.customToobar?.pageTitle?.text = getString(R.string.app_name)
        binding.customToobar?.backArrowImageView?.setOnClickListener {
            val action = ItemDetailFragmentDirections.actionItemDetailFragmentToItemListFragment()
            view.findNavController().navigate(action)
        }
        showNews()

    }
    override fun initPresenter(): ItemDetailPresenter {
        presenter = ItemDetailPresenter(this, ItemDetailModel())
        return presenter
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun showLoading(bool: Boolean) {
        TODO("Not yet implemented")
    }

    override suspend fun updateList(articleList: List<Article>) {
        TODO("Not yet implemented")
    }


    override fun showNews() {
        val currentArticle = presenter.getArticle()
        val image: ImageView? = binding.imageView

        binding.customToobar?.backToLabel?.text = getString(R.string.list)
        binding.customToobar?.menuText?.text = getString(R.string.save_article)
        binding.customToobar?.menuText?.visibility = View.VISIBLE
        binding.customToobar?.menuText?.setOnClickListener {
            navigateToSavedArticles()
        }
        binding.customToobar?.searchView?.visibility = View.GONE
        binding.title?.text = currentArticle.title
        binding.description?.text = currentArticle.description
        if(currentArticle.urlToImage.isNullOrEmpty().not()){
            Picasso.get().load(args.article.urlToImage).into(image)
        }
    }


}