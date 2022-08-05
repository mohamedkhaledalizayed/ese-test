package com.neqabty.recruitment.modules.news.view.newslist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.recruitment.R
import com.neqabty.recruitment.core.utils.AppUtils
import com.neqabty.recruitment.databinding.NewsItemBinding
import com.neqabty.recruitment.modules.news.domain.entity.NewsEntity
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
            .load(item.imageFile)
            .into(viewHolder.binding.newsImage)

        viewHolder.binding.newsTitle.text = item.title
        viewHolder.binding.newsDate.text = AppUtils().dateFormat(item.date)

        viewHolder.binding.itemLayout.setOnClickListener {
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