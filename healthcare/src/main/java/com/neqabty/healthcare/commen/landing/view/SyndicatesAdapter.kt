package com.neqabty.healthcare.commen.landing.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.healthcare.R
import com.neqabty.healthcare.commen.syndicates.domain.entity.SyndicateEntity
import com.neqabty.healthcare.databinding.SyndicateItemLayoutBinding
import com.squareup.picasso.Picasso
import kotlin.collections.ArrayList


class SyndicatesAdapter: RecyclerView.Adapter<SyndicatesAdapter.ViewHolder>() {

    private val items: ArrayList<SyndicateEntity> = ArrayList()
    private var layoutInflater: LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        val binding: SyndicateItemLayoutBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.syndicate_item_layout, parent, false)

        return ViewHolder(
            binding
        )
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val item = items[position]

        viewHolder.binding.title.text = item.name
        if (item.image.isNotEmpty()){
            Picasso.get().load(item.image).into(viewHolder.binding.image)
        }else{
            Picasso.get().load(R.drawable.eg).into(viewHolder.binding.image)
        }

    }

    override fun getItemCount() = items.size

    fun submitList(newItems: List<SyndicateEntity>?) {
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

    class ViewHolder(val binding: SyndicateItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)
}