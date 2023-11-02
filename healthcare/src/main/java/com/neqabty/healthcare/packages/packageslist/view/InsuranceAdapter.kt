package com.neqabty.healthcare.packages.packageslist.view



import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.healthcare.R
import com.neqabty.healthcare.databinding.InsuranceItemLayoutBinding
import com.neqabty.healthcare.packages.packageslist.domain.entity.insurance.InsuranceEntity
import kotlin.collections.ArrayList


class InsuranceAdapter: RecyclerView.Adapter<InsuranceAdapter.ViewHolder>() {

    private val items: MutableList<InsuranceEntity> = ArrayList()
    private var layoutInflater: LayoutInflater? = null

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        val binding: InsuranceItemLayoutBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.insurance_item_layout, parent, false)

        return ViewHolder(
            binding
        )
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = items[position]

        if (item.name.isEmpty()){
            viewHolder.binding.memberNumber.text = "وثيقة ${position.plus(1)}"
        }else{
            viewHolder.binding.memberNumber.text = item.name
        }
        viewHolder.binding.root.setOnClickListener {
            onItemClickListener?.setOnItemClickListener(item.terms_document)
        }
    }

    override fun getItemCount() = items.size

    fun submitList(newItems: List<InsuranceEntity>) {
        clear()
        newItems.let {
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
    }

    class ViewHolder(val binding: InsuranceItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)
}