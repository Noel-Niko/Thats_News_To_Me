package com.livingTechUSA.thatsnewstome.screens.SavedList

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thatsnewstome.R
import com.example.thatsnewstome.databinding.FragmentSavedArticlesBinding
import com.livingTechUSA.thatsnewstome.com.livingTechUSA.thatsnewstome.screens.SavedList.SavedArticlesModel
import com.livingTechUSA.thatsnewstome.com.livingTechUSA.thatsnewstome.screens.SavedList.SavedArticlesPresenter
import com.livingTechUSA.thatsnewstome.com.livingTechUSA.thatsnewstome.screens.SavedList.SavedArticlesView
import com.livingTechUSA.thatsnewstome.model.article.Article
import com.livingTechUSA.thatsnewstome.screens.ItemList.ItemListRecyclerViewAdapter
import com.livingTechUSA.thatsnewstome.service.coroutines.IAppDispatchers
import kotlinx.coroutines.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.coroutines.CoroutineContext

class SavedArticlesFragment: Fragment(), SavedArticlesView, CoroutineScope, KoinComponent {
    val appDispatcher: IAppDispatchers by inject()
    private val job: Job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = appDispatcher.io() + job


    private lateinit var presenter: SavedArticlesPresenter
    private lateinit var itemListAdapter: ItemListRecyclerViewAdapter
    private val nLoading: View? by lazy { inflateLoaderLayout() }
    private var articleList = mutableListOf<Article>()
    private var _binding: FragmentSavedArticlesBinding? = null
    private val binding get() = _binding!!


    open fun inflateLoaderLayout(): View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initPresenter()
        _binding = FragmentSavedArticlesBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView: RecyclerView? = binding.articleListRecyclerView


        val itemDetailFragmentContainer: View? = view.findViewById(R.id.item_detail_nav_container)
        itemListAdapter = ItemListRecyclerViewAdapter(
            articleList, itemDetailFragmentContainer,
            object : ItemListRecyclerViewAdapter.ListItemSelectListener<Article> {
                override fun onSelect(item: Article) {
                    selectItem(item)
                }
            })
        if (recyclerView != null) {
            setupRecyclerView(recyclerView)
        }
        binding.customToobar?.pageTitle?.text = getString(R.string.app_name)
        binding.customToobar.backToLabel?.text = getString(R.string.to_list)
        binding.customToobar.backArrowImageView.setOnClickListener{
            val action = SavedArticlesFragmentDirections.actionSavedArticlesFragmentToItemListFragment()
            view.findNavController().navigate(action)
        }
//        binding.customToobar?.searchView?.visibility = View.VISIBLE
//        binding.customToobar?.articleSearchView?.elevation = 3.0F
        presenter.onCreated()
    }

    private fun selectItem(item: Article) {
        val action =
            SavedArticlesFragmentDirections.actionSavedArticlesFragmentToItemDetailFragment(item)
        if (action.arguments != null) {
            view?.findNavController()?.navigate(action)
        }

    }

    override fun initPresenter(): SavedArticlesPresenter {
        presenter = SavedArticlesPresenter(this, SavedArticlesModel())
        return presenter
    }


    private fun setupRecyclerView(
        recyclerView: RecyclerView
    ) {
        recyclerView.adapter = itemListAdapter
        val linearLayoutManager = LinearLayoutManager(activity)
        binding.articleListRecyclerView?.layoutManager = linearLayoutManager
        binding.articleListRecyclerView?.adapter = itemListAdapter
    }


    override suspend fun showNews(newsHeadlines: List<Article>) {
        showLoading(true)
        updateList(newsHeadlines)
        showLoading(false)
    }


    override fun showNoArticlesFound(show: Boolean) {
        if (show) {
            Toast.makeText(context, getString(R.string.nothing_found), Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun navigateToArticleDetail(article: Article) {
        val action =
            SavedArticlesFragmentDirections.actionSavedArticlesFragmentToItemDetailFragment(article)
        this.findNavController().navigate(action)

    }

    override fun showLoading(loading: Boolean) {
        if (loading != null) {
            nLoading?.bringToFront()
            nLoading?.visibility = if (loading) View.VISIBLE else View.GONE
        }
    }

    override suspend fun updateList(articleList: List<Article>) {
        coroutineScope {
            val job = launch {
                itemListAdapter?.clearArticles()
            }
            job.join()
            launch(appDispatcher.ui()) {
                itemListAdapter?.updateList(articleList)
                showNoArticlesFound(articleList.isEmpty())
            }
        }
    }


    override fun showRecyclerViewLoader() {
        itemListAdapter?.showLoading()
        binding.nLoader?.hospiceLoading?.visibility = View.VISIBLE
    }

    override fun hideRecyclerViewLoader() {
        itemListAdapter?.hideLoading()
        binding.nLoader?.hospiceLoading?.visibility = View.GONE
    }

//    override fun showSearchViewEndDrawable(show: Boolean) {
//        if (show) {
//            binding.customToobar?.searchView?.visibility = View.VISIBLE
//        } else {
//            binding.customToobar?.searchView?.visibility = View.GONE
//        }
//
//    }
//
//    /**
//     * Extension function for showing clear text drawable in EditText
//     * */
//    private fun AppCompatEditText.showEndDrawable(show: Boolean) {
//        when {
//            show -> setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_close_edittext, 0)
//            else -> setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
//        }
//    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
