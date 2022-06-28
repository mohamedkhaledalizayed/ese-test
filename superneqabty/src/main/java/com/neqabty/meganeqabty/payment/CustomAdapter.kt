package com.neqabty.meganeqabty.payment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.neqabty.meganeqabty.R
import com.neqabty.meganeqabty.syndicates.domain.entity.ServiceEntity


class CustomAdapter(var context: Context, var syndicatesList: MutableList<ServiceEntity>) :
    BaseAdapter() {
    private var inflter: LayoutInflater? =null

    override fun getCount(): Int {
        return syndicatesList.size
    }

    override fun getItem(i: Int): ServiceEntity {
        return syndicatesList[i]
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {

        if (inflter == null){
            inflter = LayoutInflater.from(context)
        }
        var view: View = inflter!!.inflate(R.layout.spinner_item, null)

        val names = view.findViewById<View>(R.id.textView) as TextView
        names.text = syndicatesList[i].name

        return view
    }

    init {

    }
}