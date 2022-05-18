package com.livingTechUSA.thatsnewstome.screens.ItemDetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.thatsnewstome.R
import com.example.thatsnewstome.databinding.FragmentItemDetailBinding
import com.livingTechUSA.thatsnewstome.com.livingTechUSA.thatsnewstome.database.localService.ILocalService
import com.livingTechUSA.thatsnewstome.com.livingTechUSA.thatsnewstome.screens.ItemDetail.ItemDetailModel
import com.livingTechUSA.thatsnewstome.com.livingTechUSA.thatsnewstome.screens.ItemDetail.ItemDetailPresenter
import com.livingTechUSA.thatsnewstome.com.livingTechUSA.thatsnewstome.screens.ItemDetail.ItemDetailView
import com.livingTechUSA.thatsnewstome.screens.SavedList.SavedArticlesFragment
import com.livingTechUSA.thatsnewstome.service.coroutines.IAppDispatchers
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.coroutines.CoroutineContext


class ItemDetailFragment() : Fragment(), ItemDetailView, CoroutineScope, KoinComponent {
    companion object {
        fun getNewInstance(bundle: Bundle?): SavedArticlesFragment {
            val fragment = SavedArticlesFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    val appDispatcher: IAppDispatchers by inject()
    private val job: Job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = appDispatcher.io() + job

    val localService: ILocalService by inject()

    private lateinit var presenter: ItemDetailPresenter
    private val nLoading: View? by lazy { inflateLoaderLayout() }
    open fun inflateLoaderLayout(): View? = null
    private var _binding: FragmentItemDetailBinding? = null
    private val binding get() = _binding!!


    override fun showLoading(loading: Boolean) {
        if (nLoading != null) {
            nLoading?.bringToFront()
            nLoading?.visibility = if (loading) View.VISIBLE else View.GONE
        }
    }

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
        setHasOptionsMenu(true)
        return rootView
    }

    val args: ItemDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.setBundle(args.article)
        presenter.onCreated()
        setHasOptionsMenu(true)

        binding.Date?.text = args.article.publishedAt
        binding.fullTextUrl?.text = args.article.url
        binding.fullTextUrl?.setTextColor(resources.getColor(R.color.blue))
        binding.fullTextUrl?.setOnClickListener {
            val text: String = binding.fullTextUrl?.text as String
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(text))
            startActivity(browserIntent)
        }

        binding.sourceName?.text = args.article.name

        binding.customToolbar?.backToLabel?.text = getString(R.string.list)
        //binding.customToolbar?.articleSearchView?.visibility = View.GONE
        binding.customToolbar?.pageTitle?.text = getString(R.string.app_name)
        binding.customToolbar?.backArrowImageView?.setOnClickListener {
            val action = ItemDetailFragmentDirections.actionItemDetailFragmentToItemListFragment()
            view.findNavController().navigate(action)
        }

        binding.customToolbar?.hamburger?.visibility = View.VISIBLE
        binding.customToolbar?.hamburger?.setOnClickListener {
            binding?.customToolbar?.hamburger?.visibility = View.GONE
            binding.customToolbar?.save?.visibility = View.VISIBLE
            binding.customToolbar?.navToSaved?.visibility = View.VISIBLE
            binding.customToolbar?.delete?.visibility = View.VISIBLE
        }
        binding.customToolbar?.navToSaved?.setOnClickListener {
            launch(appDispatcher.io()) {
                navigateToSavedArticles()
            }
        }

        binding.customToolbar?.save?.setOnClickListener {
            launch(appDispatcher.io()) {
                localService.insertArticle(args.article)
                launch(appDispatcher.ui()) {
                    Toast.makeText(
                        context,
                        getString(R.string.success_saved),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    binding.customToolbar?.save?.visibility = View.GONE
                    binding.customToolbar?.navToSaved?.visibility = View.GONE
                    binding.customToolbar?.delete?.visibility = View.GONE
                    binding.customToolbar?.hamburger?.visibility = View.VISIBLE
                }
            }
        }

        binding.customToolbar?.delete?.setOnClickListener {
            launch(appDispatcher.io()) {
                localService.removeArticle(args.article)
                launch(appDispatcher.ui()) {
                    Toast.makeText(
                        context,
                        getString(R.string.delete_success),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    binding.customToolbar?.save?.visibility = View.GONE
                    binding.customToolbar?.navToSaved?.visibility = View.GONE
                    binding.customToolbar?.delete?.visibility = View.GONE
                    binding?.customToolbar?.hamburger?.visibility = View.VISIBLE
                }
            }
        }
        showNews()
    }


    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.hamburger, menu)
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save -> launch(appDispatcher.io()) {
                localService.insertArticle(args.article)
                launch(appDispatcher.ui()) {
                    Toast.makeText(
                        context,
                        getString(R.string.success_saved),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
            R.id.viewSaved -> launch(appDispatcher.io()) { navigateToSavedArticles() }
            R.id.delete -> launch(appDispatcher.io()) {
                localService.removeArticle(args.article)
                launch(appDispatcher.ui()) {
                    Toast.makeText(
                        context,
                        getString(R.string.delete_success),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
    }

    override fun initPresenter(): ItemDetailPresenter {
        presenter = ItemDetailPresenter(this, ItemDetailModel())
        return presenter
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override suspend fun navigateToSavedArticles() {
        val _sample = async { localService.getOneFromArticleTable() }
        val sample = _sample.await()
        if (sample?.title == null) {
            launch(appDispatcher.ui()) {
                Toast.makeText(
                    context,
                    getString(R.string.nothing_saved),
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        } else {
            launch(appDispatcher.ui()) {
                val action =
                    ItemDetailFragmentDirections.actionItemDetailFragmentToSavedArticlesFragment()
                view?.findNavController()?.navigate(action)
            }
        }
    }


    override fun showNews() {
        val currentArticle = presenter.getArticle()
        val image: ImageView? = binding.imageView
        binding.title?.text = currentArticle.title
        binding.description?.text = currentArticle.description
        if (currentArticle.urlToImage.isNullOrEmpty().not()) {
            Picasso.get().load(args.article.urlToImage).into(image)
        }
    }


}