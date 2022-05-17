package com.livingTechUSA.thatsnewstome.screens.ItemList

import android.app.LauncherActivity
import android.content.ClipData
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.thatsnewstome.R
import com.example.thatsnewstome.databinding.ItemListContentBinding
import com.google.android.material.internal.ContextUtils.getActivity
import com.livingTechUSA.thatsnewstome.Base.BaseViewHolder
import com.livingTechUSA.thatsnewstome.model.article.Article
import com.livingTechUSA.thatsnewstome.util.Constant
import com.livingTechUSA.thatsnewstome.util.Ui
import com.squareup.picasso.Picasso

class ItemListRecyclerViewAdapter(
    private val articles: MutableList<Article>,
    private val itemDetailFragmentContainer: View?,
    private val mItemSelectListener: ListItemSelectListener<Article>
) : RecyclerView.Adapter<ItemListRecyclerViewAdapter.ViewHolder>() {

    lateinit var context: Context

    private var mIsLoaderVisible: Boolean = false
    private var savedArticleList: MutableList<Article> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding =
            ItemListContentBinding.inflate(LayoutInflater.from(context), parent, false)

        return ViewHolder(context, binding)

    }

    override fun getItemCount() = articles.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(position)

        //        val item = articles[position]
//        //holder.image.
//        holder.title.text = item.title
//        holder.description.text = item.content
//
//        with(holder.itemView) {
//            tag = item
//            setOnClickListener { itemView ->
//                val bundle = Bundle()
//
//
//                val item =
//                    itemView.tag as com.livingTechUSA.thatsnewstome.placeholder .PlaceholderContent.PlaceholderItem
//
//                bundle.putString(
//                    com.livingTechUSA.thatsnewstome.screens.ItemDetail.ItemDetailFragment.ARG_ITEM_ID,
//                    item.id
//                )
//                if (itemDetailFragmentContainer != null) {
//                    itemDetailFragmentContainer.findNavController()
//                        .navigate(com.example.thatsnewstome.R.id.fragment_item_detail, bundle)
//                } else {
//                    itemView.findNavController().navigate(
//                        com.example.thatsnewstome.R.id.action_itemListFragment_to_item_detail_fragment,
//                        bundle
//                    )
//                }
//            }
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//                /**
//                 * Context click listenerI to handle Right click events
//                 * from mice and trackpad input to provide a more native
//                 * experience on larger screen devices
//                 */
//                setOnContextClickListener { v ->
//                    val item =
//                        v.tag as com.livingTechUSA.thatsnewstome.placeholder.PlaceholderContent.PlaceholderItem
//                    android.widget.Toast.makeText(
//                        v.context,
//                        "Context click of item " + item.id,
//                        android.widget.Toast.LENGTH_LONG
//                    ).show()
//                    true
//                }
//            }
//
//            setOnLongClickListener { v ->
//                // Setting the item id as the clip data so that the drop target is able to
//                // identify the id of the content
//                val clipItem = android.content.ClipData.Item(item.uniqueID)
//                val dragData = ClipData(
//                    v.tag as? CharSequence,
//                    arrayOf(android.content.ClipDescription.MIMETYPE_TEXT_PLAIN),
//                    clipItem
//                )
//
//                if (android.os.Build.VERSION.SDK_INT >= 24) {
//                    v.startDragAndDrop(
//                        dragData,
//                        android.view.View.DragShadowBuilder(v),
//                        null,
//                        0
//                    )
//                } else {
//                    v.startDrag(
//                        dragData,
//                        android.view.View.DragShadowBuilder(v),
//                        null,
//                        0
//                    )
//                }
//            }
//        }
    }


    override fun getItemViewType(position: Int): Int =
        if (mIsLoaderVisible && position == articles.size - 1) {
            Constant.RECYCLER_VIEW_TYPE_LOADING
        } else {
            Constant.RECYCLER_VIEW_TYPE_NORMAL
        }


    fun updateList(patients: List<Article>) {
        articles.clear()
        articles.addAll(patients)
        val diffCallBack = ArticleListDiffUtil.PatientListDiffCallback(savedArticleList, articles)
        val diffResult = DiffUtil.calculateDiff(diffCallBack)
        diffResult.dispatchUpdatesTo(this)
        savedArticleList.clear()
        savedArticleList.addAll(articles)

    }

    // Show/Hide loader
    fun showLoading() {
        if (!mIsLoaderVisible) {
            for (it in articles)
                savedArticleList.add(it)

        }
        mIsLoaderVisible = true
    }


    fun hideLoading() {
        if (articles.isNotEmpty() && mIsLoaderVisible) {
            mIsLoaderVisible = false
        }
    }


    fun clearArticles() {
        articles.clear()
    }


    class ArticleListDiffUtil(
        private val oldArticleList: List<Article>,
        private val newArticleList: List<Article>
    ) {
        //Only updates changes rather than redrawing the entire list.
        open class PatientListDiffCallback(
            private val oldArticleList: List<Article>,
            private val newArticleList: List<Article>
        ) : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return oldArticleList.size
            }

            override fun getNewListSize(): Int {
                return newArticleList.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldArticleList[oldItemPosition].uniqueID == newArticleList[newItemPosition].uniqueID
            }

            //Called if areItemsTheSame == true
            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldArticleList[oldItemPosition].title == newArticleList[newItemPosition].title
            }
        }

    }

    inner class ViewHolder(context: Context, val binding: ItemListContentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val mGrayColor =
            Ui.getColorInt(context, androidx.appcompat.R.color.primary_dark_material_light)
        private val mWhiteColor = Ui.getColorInt(context, R.color.white)
        private val mBlackColor = Ui.getColorInt(context, R.color.black)
        val image: ImageView = binding.imageView
        val title: TextView = binding.title
        val description: TextView = binding.description
        fun onBind(position: Int) {
            val article = articles[position]
            title.text = article.title
            description.text = article.description
            if(article.urlToImage.isNullOrEmpty().not()) {
                Picasso.get().load(article.urlToImage).into(image)
            }
            itemView.setOnClickListener { v: View ->
                article.let {
                    mItemSelectListener.onSelect(it)
                }
            }
        }
    }

    interface ListItemSelectListener<T> {
        fun onSelect(item: T)
    }

}