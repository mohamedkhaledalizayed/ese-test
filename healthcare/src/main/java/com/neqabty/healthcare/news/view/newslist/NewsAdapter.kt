package com.neqabty.healthcare.news.view.newslist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.healthcare.R
import com.neqabty.healthcare.databinding.NewsItemBinding
import com.neqabty.healthcare.news.domain.entity.NewsEntity
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


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
        viewHolder.binding.newsDate.text = dateFormat(item.createdAt.split(".")[0])

        viewHolder.binding.itemLayout.setOnClickListener {
            onItemClickListener?.setOnItemClickListener(item)
        }
    }

    @SuppressLint("SimpleDateFormat")
    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    fun dateFormat(date: String): String{
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val newDate: Date = format.parse(date)
        val arabicFormat = SimpleDateFormat("dd MMM yyy - hh:mm a", Locale("ar"))

        return arabicFormat.format(newDate.time)
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