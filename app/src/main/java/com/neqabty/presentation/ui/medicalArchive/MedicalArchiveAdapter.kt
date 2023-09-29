package com.neqabty.presentation.ui.medicalArchive

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.MedicalArchiveItemBinding
import com.neqabty.presentation.entities.ArchiveUploadItemUI
import com.neqabty.ui.presentation.common.DataBoundListAdapter

class MedicalArchiveAdapter(
    private val dataBindingComponent: DataBindingComponent,
    appExecutors: AppExecutors,
    private val callback: ((ArchiveUploadItemUI) -> Unit)?
) : DataBoundListAdapter<ArchiveUploadItemUI, MedicalArchiveItemBinding>(
        appExecutors = appExecutors,
        diffCallback = object : DiffUtil.ItemCallback<ArchiveUploadItemUI>() {
            override fun areItemsTheSame(oldItem: ArchiveUploadItemUI, newItem: ArchiveUploadItemUI): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ArchiveUploadItemUI, newItem: ArchiveUploadItemUI): Boolean {
                return oldItem.name == newItem.name
            }
        }
) {

    override fun createBinding(parent: ViewGroup): MedicalArchiveItemBinding {
        val binding = DataBindingUtil
                .inflate<MedicalArchiveItemBinding>(
                        LayoutInflater.from(parent.context),
                        R.layout.medical_archive_item,
                        parent,
                        false,
                        dataBindingComponent
                )
        binding.root.setOnClickListener {
            binding.item?.let {
                callback?.invoke(it)
            }
        }

//        binding.ivLogo.layoutParams.height = DisplayMetrics.width / 6
//        binding.ivLogo.requestLayout()

        return binding
    }

    override fun bind(binding: MedicalArchiveItemBinding, item: ArchiveUploadItemUI) {
        binding.item = item
    }
}
