package com.neqabty.healthcare.complains.view

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.healthcare.R
import com.neqabty.healthcare.complains.domain.entity.ComplainEntity
import com.neqabty.healthcare.databinding.ComplainItemBinding
import java.text.SimpleDateFormat
import java.util.*


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
        viewHolder.binding.answer.text = item.answer ?: ""
        viewHolder.binding.date.text = dateFormat(item.createdAt.split(".")[0])
        if (item.answer.isNullOrEmpty()){
            viewHolder.binding.statusText.text = "الطلب قيد الانتظار"
            viewHolder.binding.statusText.setTextColor(Color.argb(100, 255, 180, 40))
            viewHolder.binding.statusImage.setImageResource(R.color.orange)
        }else{
            viewHolder.binding.statusText.text = "تم الرد علي الطلب"
            viewHolder.binding.statusText.setTextColor(Color.argb(100, 0, 167, 87))
            viewHolder.binding.statusImage.setImageResource(R.color.green)
        }
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