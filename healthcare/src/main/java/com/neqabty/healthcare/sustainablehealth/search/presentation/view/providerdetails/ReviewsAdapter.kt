package com.neqabty.healthcare.sustainablehealth.search.presentation.view.providerdetails

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.healthcare.R
import com.neqabty.healthcare.databinding.ReviewItemBinding
import com.neqabty.healthcare.sustainablehealth.search.presentation.model.search.PackageInfo
import kotlin.collections.ArrayList


class ReviewsAdapter: RecyclerView.Adapter<ReviewsAdapter.ViewHolder>() {

    private val items: MutableList<PackageInfo> = ArrayList()
    private var layoutInflater: LayoutInflater? = null

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        val binding: ReviewItemBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.review_item, parent, false)

        return ViewHolder(
            binding
        )
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

    }

    override fun getItemCount() = 10

    fun submitList(newItems: MutableList<PackageInfo>?) {
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
            fun setOnItemClickListener(item: String)
            fun setOnRegisterClickListener(item: String)
    }

    class ViewHolder(val binding: ReviewItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}