package com.neqabty.news.modules.home.presentation.view.newslist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.news.R
import com.neqabty.news.databinding.NewsItemBinding
import com.neqabty.news.modules.home.domain.entity.NewsEntity
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso


class NewsAdapter: RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    private val items: MutableList<NewsEntity> = ArrayList()
    private var layoutInflater: LayoutInflater? = null

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        val binding: NewsItemBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.news_item, parent, false)

        return ViewHolder(
            binding
        )
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val item = items[position]
        Picasso.get()
            .load(item.image)
            .into(viewHolder.binding.newsImage, object : Callback {
                override fun onSuccess() {
//                    viewHolder.binding.imageProgress.hide()
                }

                override fun onError(e: Exception?) {
//                    viewHolder.binding.imageProgress.hide()
                }
            })

        viewHolder.binding.newsTitle.text = item.headline
        viewHolder.binding.newsDate.text = item.createdAt

        viewHolder.binding.newsImage.setOnClickListener {
            onItemClickListener?.setOnItemClickListener(item)
        }
    }

    override fun getItemCount() = items.size

    fun submitList(newItems: List<NewsEntity>?) {
        clear()
        newItems?.let {
            items.addAll(it)
            notifyDataSetChanged()
        }
    }

    @Suppress("unused")
    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
            fun setOnItemClickListener(item: NewsEntity)
    }

    class ViewHolder(val binding: NewsItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}