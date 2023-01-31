package com.neqabty.healthcare.sustainablehealth.home.presentation.view.homescreen.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.healthcare.R
import com.neqabty.healthcare.commen.syndicates.domain.entity.SyndicateEntity
import com.neqabty.healthcare.databinding.SyndicateBinding
import com.neqabty.healthcare.sustainablehealth.home.domain.entity.about.AboutEntity
import com.squareup.picasso.Picasso


class SyndicatesAdapter: RecyclerView.Adapter<SyndicatesAdapter.ViewHolder>() {

    private val items: MutableList<SyndicateEntity> = ArrayList()
    private var layoutInflater: LayoutInflater? = null

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        val binding: SyndicateBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.syndicate, parent, false)

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
        }
//        viewHolder.binding.itemLayout.setOnClickListener {
//            onItemClickListener?.setOnItemClickListener(items[position].key, items[position].value)
//        }
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

    interface OnItemClickListener {
            fun setOnItemClickListener(title: String, content: String)
    }

    class ViewHolder(val binding: SyndicateBinding) :
        RecyclerView.ViewHolder(binding.root)
}