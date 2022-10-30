package com.neqabty.shealth.sustainablehealth.offers.presentation.view.cashback

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.shealth.R
import com.neqabty.shealth.databinding.PersonItemBinding
import kotlin.collections.ArrayList


class PersonAdapter: RecyclerView.Adapter<PersonAdapter.ViewHolder>() {

    private val items: MutableList<String> = ArrayList()
    private var layoutInflater: LayoutInflater? = null

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        val binding: PersonItemBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.person_item, parent, false)

        return ViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {


    }

    override fun getItemCount() = 12

    fun submitList(newItems: MutableList<String>?) {
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
            fun setOnTakeClickListener(item: String)
            fun setOnCashBackClickListener(item: String)
    }

    class ViewHolder(val binding: PersonItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}