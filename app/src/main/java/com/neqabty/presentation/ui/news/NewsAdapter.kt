package com.neqabty.presentation.ui.news

import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.NewsItemBinding
import com.neqabty.presentation.entities.NewsUI
import com.neqabty.presentation.util.DisplayMetrics
import com.neqabty.ui.presentation.common.DataBoundListAdapter

class NewsAdapter(
    private val dataBindingComponent: DataBindingComponent,
    appExecutors: AppExecutors,
    private val callback: ((NewsUI) -> Unit)?
) : DataBoundListAdapter<NewsUI, NewsItemBinding>(
        appExecutors = appExecutors,
        diffCallback = object : DiffUtil.ItemCallback<NewsUI>() {
            override fun areItemsTheSame(oldItem: NewsUI, newItem: NewsUI): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: NewsUI, newItem: NewsUI): Boolean {
                return oldItem.img == newItem.img
            }
        }
) {

    override fun createBinding(parent: ViewGroup): NewsItemBinding {
        val binding = DataBindingUtil
                .inflate<NewsItemBinding>(
                        LayoutInflater.from(parent.context),
                        R.layout.news_item,
                        parent,
                        false,
                        dataBindingComponent
                )
        binding.root.setOnClickListener {
            binding.news?.let {
                callback?.invoke(it)
            }
        }

        binding.ivLogo.layoutParams.height = DisplayMetrics.width / 6
        binding.ivLogo.requestLayout()

        return binding
    }

    override fun bind(binding: NewsItemBinding, item: NewsUI) {
        binding.news = item
    }
}
