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
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.thatsnewstome.R
import com.example.thatsnewstome.databinding.ItemListContentBinding
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
        notifyDataSetChanged()
        /*TODO: Address glich with diffCallBack causing crash:
        java.lang.IndexOutOfBoundsException: Inconsistency detected. Invalid view holder adapter positionViewHolder{3...
        ...due to the hashMap being recreated by the navGraph with each return to prior
        screen resulting in discrepencies when calculating recyclerview positions.
         */
//        val diffCallBack = ArticleListDiffUtil.PatientListDiffCallback(savedArticleList, articles)
//        val diffResult = DiffUtil.calculateDiff(diffCallBack)
//        diffResult.dispatchUpdatesTo(this)
        savedArticleList.clear()
        savedArticleList.addAll(articles)

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
                return oldArticleList[oldItemPosition].title == newArticleList[newItemPosition].title
            }

            //Called if areItemsTheSame == true
            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldArticleList[oldItemPosition].title == newArticleList[newItemPosition].title
            }
        }

    }

    inner class ViewHolder(context: Context, val binding: ItemListContentBinding) :
        RecyclerView.ViewHolder(binding.root) {
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