package com.neqabty.presentation.ui.claiming

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.neqabty.R
import com.neqabty.presentation.entities.ProviderUI
import kotlinx.android.synthetic.main.claiming_provider_item.view.*

class ProvidersSpinnerAdapter(ctx: Context, var providers: List<ProviderUI>) :
    ArrayAdapter<ProviderUI>(ctx, 0, providers) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createItemView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val provider = getItem(position)

        val view = LayoutInflater.from(context).inflate(
            R.layout.claiming_provider_item,
            parent,
            false
        )

        provider?.let {
            view.tvName.text = provider.name
            view.tvAddress.text = provider.address
            view.tvPhone.text = provider.phones
            view.lineView.visibility = if(position == providers.size -1) View.GONE else View.VISIBLE
        }
        return view
    }

    fun createItemView(position: Int, recycledView: View?, parent: ViewGroup): View {
        val provider = getItem(position)

        val view = recycledView ?: LayoutInflater.from(context).inflate(
            R.layout.spinner_item,
            parent,
            false
        )

        provider?.let {
            (view as TextView).text = provider.name
        }
        return view
    }
}