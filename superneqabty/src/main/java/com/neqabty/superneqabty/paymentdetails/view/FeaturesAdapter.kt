package com.neqabty.superneqabty.paymentdetails.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.superneqabty.R
import com.neqabty.superneqabty.databinding.FeatureLayoutItemBinding
import com.neqabty.superneqabty.databinding.ServiceLayoutItemBinding
import com.neqabty.superneqabty.home.domain.entity.NewsEntity
import com.neqabty.superneqabty.paymentdetails.data.model.inquiryresponse.Feature


class FeaturesAdapter: RecyclerView.Adapter<FeaturesAdapter.ViewHolder>() {

    private val items: MutableList<Feature> = ArrayList()
    private var layoutInflater: LayoutInflater? = null

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        val binding: FeatureLayoutItemBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.feature_layout_item, parent, false)

        return ViewHolder(
            binding
        )
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val item = items[position]
        viewHolder.binding.feature.text = item.name

        viewHolder.binding.feature.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                onItemClickListener?.setOnItemCheckedListener(item.id)
            }else{
                onItemClickListener?.setOnItemUnCheckedListener(item.id)
            }
        }


    }

    override fun getItemCount() = items.size

    fun submitList(newItems: List<Feature>?) {
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
            fun setOnItemCheckedListener(id: Int)
            fun setOnItemUnCheckedListener(id: Int)
    }

    class ViewHolder(val binding: FeatureLayoutItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}