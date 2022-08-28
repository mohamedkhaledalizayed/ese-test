package com.neqabty.meganeqabty.complains.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.meganeqabty.R
import com.neqabty.meganeqabty.complains.domain.entity.ComplainEntity
import com.neqabty.meganeqabty.databinding.ComplainItemBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ComplainsAdapter: RecyclerView.Adapter<ComplainsAdapter.ViewHolder>() {

    private val items: MutableList<ComplainEntity> = ArrayList()
    private var layoutInflater: LayoutInflater? = null

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        val binding: ComplainItemBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.complain_item, parent, false)

        return ViewHolder(binding)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val item = items[position]
        viewHolder.binding.complain.text = item.message
        viewHolder.binding.answer.text = item.answer ?: "لم يتم الرد"
        viewHolder.binding.date.text = dateFormat(item.createdAt.split(".")[0])
    }

    override fun getItemCount() = items.size

    fun submitList(newItems: MutableList<ComplainEntity>?) {
        clear()
        newItems?.let {
            items.addAll(it)
            notifyDataSetChanged()
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

    @Suppress("unused")
    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
            fun setOnItemClickListener(item: String)
    }

    class ViewHolder(val binding: ComplainItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}