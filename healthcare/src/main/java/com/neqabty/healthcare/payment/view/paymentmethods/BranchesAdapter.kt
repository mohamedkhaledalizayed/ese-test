package com.neqabty.healthcare.payment.view.paymentmethods


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.neqabty.healthcare.R
import com.neqabty.healthcare.payment.domain.entity.branches.BranchesEntity


class BranchesAdapter : BaseAdapter() {

    private var listItems: MutableList<BranchesEntity> = ArrayList()
    var onItemClickListener: OnItemClickListener? = null
    private var inflter: LayoutInflater? =null

    override fun getCount(): Int {
        return listItems.size
    }

    override fun getItem(i: Int): Any {
        return listItems[i]
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    override fun getView(position: Int, view: View?, viewGroup: ViewGroup): View {

        if (inflter == null){
            inflter = LayoutInflater.from(viewGroup.context)
        }
        var view: View = inflter!!.inflate(R.layout.spinner_item, null)

        val item = listItems[position]

        val names = view.findViewById<View>(R.id.textView) as TextView
        names.text = item.city

        return view
    }

    interface OnItemClickListener {
        fun setOnItemClickListener(item: String)
    }

    fun submitList(newItems: MutableList<BranchesEntity>?) {
        listItems.clear()
        newItems?.let {
            listItems.addAll(it)
            notifyDataSetChanged()
        }
    }
}