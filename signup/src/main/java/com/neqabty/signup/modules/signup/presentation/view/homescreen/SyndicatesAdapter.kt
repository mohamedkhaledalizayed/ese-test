package com.neqabty.signup.modules.signup.presentation.view.homescreen


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.neqabty.signup.R
import com.neqabty.signup.modules.signup.domain.entity.syndicate.SyndicateListEntity


class SyndicatesAdapter() : BaseAdapter() {

    private var listItems: MutableList<SyndicateListEntity> = ArrayList()
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
        names.text = item.name

        return view
    }

    interface OnItemClickListener {
        fun setOnItemClickListener(item: String)
    }

    fun submitList(newItems: MutableList<SyndicateListEntity>?) {
        listItems.clear()
        newItems?.let {
            listItems.addAll(it)
            notifyDataSetChanged()
        }
    }
}