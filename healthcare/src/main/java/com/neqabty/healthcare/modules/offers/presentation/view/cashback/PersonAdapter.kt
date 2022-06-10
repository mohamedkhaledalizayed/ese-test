package com.neqabty.healthcare.modules.offers.presentation.view.cashback

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.healthcare.R
import com.neqabty.healthcare.databinding.OfferItemBinding
import com.neqabty.healthcare.databinding.PersonItemBinding
import com.squareup.picasso.Picasso
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

        if (position % 3 == 0){
            viewHolder.binding.statusImage.setImageResource(R.drawable.ic_cancel)
            viewHolder.binding.statusText.text = "لم يتم التسجيل"
        }else{
            viewHolder.binding.statusImage.setImageResource(R.drawable.ic_success_icon_green)
            viewHolder.binding.statusText.text = "تم تسجيل الاشتراك بنجاح"
        }
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