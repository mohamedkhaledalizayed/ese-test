package com.neqabty.presentation.ui.common

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.PhotoItemBinding
import com.neqabty.presentation.entities.PhotoUI
import com.neqabty.presentation.util.DisplayMetrics
import com.neqabty.ui.presentation.common.DataBoundListAdapter

class PhotosAdapter(
        private val dataBindingComponent: DataBindingComponent,
        appExecutors: AppExecutors,
        private val callback: ((PhotoUI) -> Unit)?
) : DataBoundListAdapter<PhotoUI, PhotoItemBinding>(
        appExecutors = appExecutors,
        diffCallback = object : DiffUtil.ItemCallback<PhotoUI>() {
            override fun areItemsTheSame(oldItem: PhotoUI, newItem: PhotoUI): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: PhotoUI, newItem: PhotoUI): Boolean {
                return oldItem.name == newItem.name
            }
        }
) {

    override fun createBinding(parent: ViewGroup): PhotoItemBinding {
        val binding = DataBindingUtil
                .inflate<PhotoItemBinding>(
                        LayoutInflater.from(parent.context),
                        R.layout.photo_item,
                        parent,
                        false,
                        dataBindingComponent
                )
        binding.root.setOnClickListener {
            binding.photo?.let {
                callback?.invoke(it)
            }
        }
        binding.root.layoutParams = ViewGroup.LayoutParams(DisplayMetrics.width / 4, DisplayMetrics.width / 4)
        return binding
    }

    override fun bind(binding: PhotoItemBinding, item: PhotoUI) {
        binding.photo = item
//        val uri : Uri ? = Uri.parse(item.path + item.name)
        binding.ivImage.setImageURI(item.uri)
    }
}
