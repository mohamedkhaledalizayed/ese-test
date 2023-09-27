package com.neqabty.healthcare.pharmacymart.orders.ui.orderdetails

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.data.Constants
import com.neqabty.healthcare.databinding.PrescriptionItemBinding


class ImagesAdapter : RecyclerView.Adapter<ImagesAdapter.ViewHolder>() {

    private val items: MutableList<String> = ArrayList()
    private var layoutInflater: LayoutInflater? = null

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        val binding: PrescriptionItemBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.prescription_item, parent, false)

        return ViewHolder(
            binding
        )
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val image = items[position]
        viewHolder.binding.root.setOnClickListener { onItemClickListener?.setOnDeleteClickListener(position) }
        if (convertBase64(image) != null){
            viewHolder.binding.image.setImageBitmap(convertBase64(image))
        }
    }

    private fun convertBase64(encodedImage: String): Bitmap?{
        return try {
            val decodedString: ByteArray = Base64.decode(encodedImage, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        }catch (e: Exception){
            null
        }
    }

    override fun getItemCount() = items.size

    fun submitList(newItems: List<String>?) {
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
        fun setOnDeleteClickListener(position: Int)
    }

    class ViewHolder(val binding: PrescriptionItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}