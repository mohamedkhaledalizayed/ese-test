package com.neqabty.healthcare.modules.suggestions.presentation


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.neqabty.healthcare.R
import com.neqabty.healthcare.modules.search.presentation.model.filters.ItemUi
import com.neqabty.healthcare.modules.suggestions.domain.entity.CategoryEntity


class CategoriesAdapter() : BaseAdapter() {

    private var listItems: MutableList<CategoryEntity> = ArrayList()
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
        names.text = item.nameAr

        return view
    }

    interface OnItemClickListener {
        fun setOnItemClickListener(item: String)
    }

    fun submitList(newItems: MutableList<CategoryEntity>?) {
        listItems.clear()
        newItems?.let {
            listItems.addAll(it)
            notifyDataSetChanged()
        }
    }
}