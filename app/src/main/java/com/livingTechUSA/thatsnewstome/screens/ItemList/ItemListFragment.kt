package com.livingTechUSA.thatsnewstome.screens.ItemList

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thatsnewstome.R
import com.example.thatsnewstome.databinding.FragmentItemListBinding
import com.livingTechUSA.thatsnewstome.model.article.Article
import com.livingTechUSA.thatsnewstome.service.api.NewsApiResponse
import com.livingTechUSA.thatsnewstome.service.coroutines.IAppDispatchers
import com.livingTechUSA.thatsnewstome.service.remoteService.IOnFetchDataListener
import kotlinx.coroutines.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.coroutines.CoroutineContext

class ItemListFragment : Fragment(), ItemListView, CoroutineScope, KoinComponent {
    val appDispatcher: IAppDispatchers by inject()
    private val listener: IOnFetchDataListener<NewsApiResponse> by inject()
private val job: Job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = appDispatcher.io() + job


    private lateinit var presenter: ItemListPresenter
    private lateinit var itemListAdapter: ItemListRecyclerViewAdapter
    private val nLoading: View? by lazy { inflateLoaderLayout() }
    private var articleList = mutableListOf<Article>()
    private var _binding: FragmentItemListBinding? = null
    private val binding get() = _binding!!


    open fun inflateLoaderLayout(): View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initPresenter()
        _binding = FragmentItemListBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView: RecyclerView? = binding.articleListRecyclerView


        val itemDetailFragmentContainer: View? = view.findViewById(R.id.item_detail_nav_container)
        itemListAdapter = ItemListRecyclerViewAdapter(
            articleList,  itemDetailFragmentContainer,
            object : ItemListRecyclerViewAdapter.ListItemSelectListener<Article> {
            override fun onSelect(item: Article) {
                        selectItem(item)
                    }
                })
        if (recyclerView != null) {
            setupRecyclerView(recyclerView)
        }
        binding.customToobar?.pageTitle?.text = getString(R.string.app_name)
        binding.customToobar?.backToLabel?.text = getString(R.string.exit)
//        binding.customToobar?.searchView?.visibility = View.VISIBLE
//        binding.customToobar?.articleSearchView?.elevation = 3.0F
        presenter.onCreated()
    }

    private fun selectItem(item: Article) {
        val action =
            ItemListFragmentDirections.actionItemListFragmentToItemDetailFragment(item)
        if (action.arguments != null) {
            view?.findNavController()?.navigate(action)
        }

    }

    override fun initPresenter(): ItemListPresenter {
        presenter = ItemListPresenter(this, ItemListModel())
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
            Toast.makeText(context, "No articles found for search criteria.", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun navigateToArticleDetail(article: Article) {
        val action =
            ItemListFragmentDirections.actionItemListFragmentToItemDetailFragment(article)
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

//
//    override fun showSearchViewEndDrawable(show: Boolean) {
//        if(show){
//            binding.customToobar?.searchView?.visibility = View.VISIBLE
//        }else {
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
//
//    /**
//     * Shows/Hides search view
//     * */
//    private fun showSearchView(show: Boolean) {
//        launch(appDispatcher.ui()) {
//                if (show){
//                    binding.customToobar?.articleSearchView?.visibility = View.VISIBLE
//                    binding.customToobar?.searchView?.visibility = View.GONE
//                } else View.GONE
//            if (!show) {
//                presenter.clearSearchText()
//                binding.customToobar?.articleSearchView?.setText("")
//                binding.customToobar?.articleSearchView?.visibility = View.GONE
//                binding.customToobar?.searchView?.visibility = View.VISIBLE
//            }
//            presenter.setSearchSelected(show)
//        }
//    }
//
//    /**
//     * invalidateOptionsMenu() calls onCreateOptionsMenu() and resets the search bar as well as the menu icon
//     * */
//    override fun clearSearchTextIfAny() {
//        showSearchView(false)
//        activity?.invalidateOptionsMenu()
//    }
//
//
//    override fun setSearchQueryTextListener() {
//        binding.customToobar?.articleSearchView?.doAfterTextChanged {
//            presenter.onSearchQueryChange(
//                it?.toString() ?: ""
//            )
//        }
//    }
//
//    override fun showErrorMessage(show: Boolean, message: String) {
//        binding.errorMessage?.apply {
//            isVisible = show
//            text = message
//        }
//    }
//    /**
//     * Extension function for clearing search in EditText
//     * */
//    @SuppressLint("ClickableViewAccessibility")
//    private fun AppCompatEditText.setRightDrawableOnTouchListener(func: AppCompatEditText.() -> Unit) {
//        setOnTouchListener { view, event ->
//            var isClicked = false
//            if (event.action == MotionEvent.ACTION_UP) {
//                val drawable = compoundDrawables[2]
//                drawable?.let {
//                    if (event.rawX >= (right - drawable.bounds.width())) {
//                        func()
//                        isClicked = true
//                    }
//                }
//            }
//            isClicked
//        }
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}