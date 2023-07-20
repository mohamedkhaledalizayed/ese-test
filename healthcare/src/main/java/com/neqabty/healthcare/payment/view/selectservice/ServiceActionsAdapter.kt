package com.neqabty.healthcare.payment.view.selectservice


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.neqabty.healthcare.R
import com.neqabty.healthcare.payment.domain.entity.serviceactions.ServiceActionsEntity

class ServiceActionsAdapter(context: Context, courseModelArrayList: MutableList<ServiceActionsEntity?>) :
    ArrayAdapter<ServiceActionsEntity?>(context, 0, courseModelArrayList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var listitemView = convertView
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(context).inflate(R.layout.service_action_layout, parent, false)
        }

        val item = getItem(position)
        val serviceName = listitemView!!.findViewById<TextView>(R.id.service_name)

        serviceName.text = item?.name
        return listitemView
    }
}

//class ServiceActionsAdapter: RecyclerView.Adapter<ServiceActionsAdapter.ViewHolder>() {
//
//    private val items: MutableList<ServiceActionsEntity> = ArrayList()
//    private var layoutInflater: LayoutInflater? = null
//
//    var onItemClickListener: OnItemClickListener? = null
//
//    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
//
//        if (layoutInflater == null) {
//            layoutInflater = LayoutInflater.from(parent.context)
//        }
//
//        val binding: ServiceActionLayoutBinding =
//            DataBindingUtil.inflate(layoutInflater!!, R.layout.service_action_layout, parent, false)
//
//        return ViewHolder(
//            binding
//        )
//    }
//
//    @SuppressLint("ResourceAsColor")
//    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
//        val item = items[position]
//
//        viewHolder.binding.serviceName.text = item.name
//        viewHolder.binding.root.setOnClickListener {
//            onItemClickListener?.setOnDeleteFollowerListener(item)
//        }
//    }
//
//    override fun getItemCount() = items.size
//
//    fun submitList(newItems: List<ServiceActionsEntity>) {
//        clear()
//        newItems.let {
//            items.addAll(it)
//            notifyDataSetChanged()
//        }
//    }
//
//    @Suppress("unused")
//    fun clear() {
//        items.clear()
//        notifyDataSetChanged()
//    }
//
//    interface OnItemClickListener {
//            fun setOnDeleteFollowerListener(service: ServiceActionsEntity)
//    }
//
//    class ViewHolder(val binding: ServiceActionLayoutBinding) :
//        RecyclerView.ViewHolder(binding.root)
//}