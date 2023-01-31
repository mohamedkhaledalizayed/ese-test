package com.neqabty.healthcare.sustainablehealth.home.presentation.view.homescreen.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.healthcare.R
import com.neqabty.healthcare.databinding.SyndicateBinding
import com.neqabty.healthcare.sustainablehealth.home.domain.entity.about.AboutEntity


class SyndicatesAdapter: RecyclerView.Adapter<SyndicatesAdapter.ViewHolder>() {

    private val items: MutableList<AboutEntity> = ArrayList()
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

        if ( position == 2 || position == 3){
            viewHolder.binding.soon.visibility = View.VISIBLE
        }else{
            viewHolder.binding.soon.visibility = View.GONE
        }

        when (position) {
            0 -> {
                viewHolder.binding.image.setImageResource(R.drawable.logo_1)
                viewHolder.binding.title.text = "نقابة المهندسيين"
            }
            1 -> {
                viewHolder.binding.image.setImageResource(R.drawable.logo_2)
                viewHolder.binding.title.text = "النقابة العامة\n" +
                        " للمرشدين السياحين"
            }
            2 -> {
                viewHolder.binding.image.setImageResource(R.drawable.logo_3)
                viewHolder.binding.title.text = "نقابة المحاميين"
            }
            3 -> {
                viewHolder.binding.image.setImageResource(R.drawable.logo_4)
                viewHolder.binding.title.text = "نقابة التجاريين"
            }
        }

//        viewHolder.binding.aboutSeha.text = items[position].key
//
//        viewHolder.binding.itemLayout.setOnClickListener {
//            onItemClickListener?.setOnItemClickListener(items[position].key, items[position].value)
//        }
    }

    override fun getItemCount() = 8

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

    class ViewHolder(val binding: SyndicateBinding) :
        RecyclerView.ViewHolder(binding.root)
}