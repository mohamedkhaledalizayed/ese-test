
package com.neqabty.meganeqabty.syndicates.presentation.view.homescreen


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.meganeqabty.R
import com.neqabty.meganeqabty.databinding.SyndicateItemBinding
import com.neqabty.meganeqabty.syndicates.domain.entity.SyndicateEntity
import com.squareup.picasso.Picasso


class SyndicateAdapter: BaseAdapter() {

    var onItemClickListener: OnItemClickListener? = null
    private var mList: ArrayList<SyndicateEntity> = ArrayList()
    private var layoutInflater: LayoutInflater? = null
    private lateinit var image: ImageView
    private lateinit var title: TextView
    private lateinit var itemLayout: ConstraintLayout

    override fun getCount(): Int {
        return mList.size
    }
    override fun getItem(position: Int): Any? {
        return null
    }
    override fun getItemId(position: Int): Long {
        return 0
    }
    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View? {
        var convertView = convertView
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }
        if (convertView == null) {
            convertView = layoutInflater!!.inflate(R.layout.syndicate_item, null)
        }

        val item = mList[position]
        itemLayout = convertView!!.findViewById(R.id.item_view)
        image = convertView.findViewById(R.id.image)
        title = convertView.findViewById(R.id.title)

        itemLayout.setOnClickListener {
            onItemClickListener?.setOnItemClickListener(item)
        }

        title.text = item.name
        if (item.image.isNotEmpty()){
            Picasso.get().load(item.image).into(image)
        }
        return convertView
    }

    class MyViewHolder(val binding: SyndicateItemBinding) : RecyclerView.ViewHolder(binding.root)

    fun submitList(list: List<SyndicateEntity>?) {
        list?.let {
            mList.addAll(it)
            notifyDataSetChanged()
        }
    }

    @Suppress("unused")
    fun clear() {
        mList.clear()
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun setOnItemClickListener(item: SyndicateEntity)
    }
}