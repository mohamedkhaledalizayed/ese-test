package com.neqabty.healthcare.core.home_syndicates.view


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.healthcare.R
import com.neqabty.healthcare.commen.syndicateservices.domain.entity.SyndicateServiceEntity
import com.neqabty.healthcare.databinding.ListItemSyndicateServiceBinding
import com.neqabty.healthcare.databinding.NewsItemBinding
import com.neqabty.healthcare.news.domain.entity.NewsEntity
import com.neqabty.healthcare.news.view.newslist.NewsAdapter
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class SyndicateServicesAdapter: RecyclerView.Adapter<SyndicateServicesAdapter.ViewHolder>() {

    private val items: MutableList<SyndicateServiceEntity> = ArrayList()
    private var layoutInflater: LayoutInflater? = null

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        val binding: ListItemSyndicateServiceBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.list_item_syndicate_service, parent, false)

        return ViewHolder(
            binding
        )
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val item = items[position]
//        Picasso.get()
//            .load(item.name)
//            .into(viewHolder.binding.ivIcon, object : Callback {
//                override fun onSuccess() {
////                    viewHolder.binding.imageProgress.hide()
//                }
//
//                override fun onError(e: Exception?) {
////                    viewHolder.binding.imageProgress.hide()
//                }
//            })

        viewHolder.binding.tvName.text = item.name
        viewHolder.binding.llHolder.setOnClickListener {
            onItemClickListener?.setOnItemClickListener(item)
        }
    }

    override fun getItemCount() = items.size

    fun submitList(newItems: List<SyndicateServiceEntity>?) {
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
        fun setOnItemClickListener(item: SyndicateServiceEntity)
    }

    class ViewHolder(val binding: ListItemSyndicateServiceBinding) :
        RecyclerView.ViewHolder(binding.root)
}