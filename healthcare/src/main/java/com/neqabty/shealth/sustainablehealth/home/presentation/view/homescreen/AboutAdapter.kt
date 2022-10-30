package com.neqabty.shealth.sustainablehealth.home.presentation.view.homescreen

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.shealth.R
import com.neqabty.shealth.databinding.AboutItemBinding
import com.neqabty.shealth.sustainablehealth.home.domain.entity.about.AboutEntity


class AboutAdapter: RecyclerView.Adapter<AboutAdapter.ViewHolder>() {

    private val items: MutableList<AboutEntity> = ArrayList()
    private var layoutInflater: LayoutInflater? = null

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        val binding: AboutItemBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.about_item, parent, false)

        return ViewHolder(
            binding
        )
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.aboutSeha.text = items[position].key

        viewHolder.binding.itemLayout.setOnClickListener {
            onItemClickListener?.setOnItemClickListener(items[position].key, items[position].value)
        }
    }

    override fun getItemCount() = items.size

    fun submitList(newItems: List<AboutEntity>?) {
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

    class ViewHolder(val binding: AboutItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}