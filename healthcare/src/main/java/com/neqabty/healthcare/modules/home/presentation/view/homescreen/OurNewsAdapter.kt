package com.neqabty.healthcare.modules.home.presentation.view.homescreen

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.healthcare.R
import com.neqabty.healthcare.databinding.OurNewsItemBinding
import com.neqabty.meganeqabty.core.utils.AppUtils
import com.neqabty.news.modules.home.domain.entity.NewsEntity
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso


class OurNewsAdapter: RecyclerView.Adapter<OurNewsAdapter.ViewHolder>() {

    private val items: MutableList<NewsEntity> = ArrayList()
    private var layoutInflater: LayoutInflater? = null

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        val binding: OurNewsItemBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.our_news_item, parent, false)

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
        viewHolder.binding.newsDate.text = AppUtils().dateFormat(item.createdAt)

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

    class ViewHolder(val binding: OurNewsItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}