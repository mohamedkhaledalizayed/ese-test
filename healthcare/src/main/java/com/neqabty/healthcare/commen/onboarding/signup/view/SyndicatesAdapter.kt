package com.neqabty.healthcare.commen.onboarding.signup.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.healthcare.R
import com.neqabty.healthcare.commen.syndicates.domain.entity.SyndicateEntity
import com.neqabty.healthcare.databinding.SignUpSyndicateItemLayoutBinding
import com.squareup.picasso.Picasso


class SyndicatesAdapter(private val hasCustomStyle: Boolean = false) :
    RecyclerView.Adapter<SyndicatesAdapter.ViewHolder>() {

    private val items: ArrayList<SyndicateEntity> = ArrayList()
    private var layoutInflater: LayoutInflater? = null
    var onItemClickListener: SyndicatesAdapter.OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        val binding: SignUpSyndicateItemLayoutBinding =
            DataBindingUtil.inflate(
                layoutInflater!!,
                R.layout.sign_up_syndicate_item_layout,
                parent,
                false
            )

        return ViewHolder(
            binding
        )
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val item = items[position]

        viewHolder.binding.tvName.text = item.name
        if (item.image.isNotEmpty()) {
            Picasso.get().load(item.image).into(viewHolder.binding.ivLogo)
        } else {
            Picasso.get().load(R.drawable.eg).into(viewHolder.binding.ivLogo)
        }
        if (!hasCustomStyle) {
            viewHolder.binding.clContainer.elevation = 0F
            viewHolder.binding.clContainer.background = null
        }

        viewHolder.binding.clContainer.setOnClickListener {
            onItemClickListener?.setOnItemClickListener(item)
        }
    }

    override fun getItemCount() = items.size

    fun submitList(newItems: List<SyndicateEntity>?) {
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
        fun setOnItemClickListener(item: SyndicateEntity)
    }

    class ViewHolder(val binding: SignUpSyndicateItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)
}