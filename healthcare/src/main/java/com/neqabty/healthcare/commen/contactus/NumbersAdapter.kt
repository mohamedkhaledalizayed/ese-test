package com.neqabty.healthcare.commen.contactus


import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.neqabty.healthcare.databinding.FragmentItemBinding


class NumbersAdapter(
    private val values: List<String>
) : RecyclerView.Adapter<NumbersAdapter.ViewHolder>() {

    var onItemClickListener: OnItemClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.binding.content.text = item

        holder.binding
        holder.binding.content.setOnClickListener {
            onItemClickListener?.setOnItemClickListener(item)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(val binding: FragmentItemBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnItemClickListener {
        fun setOnItemClickListener(item: String)
    }

}