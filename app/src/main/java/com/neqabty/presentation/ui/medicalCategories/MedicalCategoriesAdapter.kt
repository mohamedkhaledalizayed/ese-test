package com.neqabty.presentation.ui.medicalCategories

import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.support.constraint.ConstraintLayout
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.MedicalCategoryItemBinding
import com.neqabty.presentation.util.DisplayMetrics
import com.neqabty.ui.presentation.common.DataBoundListAdapter

class MedicalCategoriesAdapter(
        private val dataBindingComponent: DataBindingComponent,
        appExecutors: AppExecutors,
        private val callback: ((MedicalCategoryUI) -> Unit)?
) : DataBoundListAdapter<MedicalCategoryUI, MedicalCategoryItemBinding>(
        appExecutors = appExecutors,
        diffCallback = object : DiffUtil.ItemCallback<MedicalCategoryUI>() {
            override fun areItemsTheSame(oldItem: MedicalCategoryUI, newItem: MedicalCategoryUI): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MedicalCategoryUI, newItem: MedicalCategoryUI): Boolean {
                return oldItem.name == newItem.name
            }
        }
) {

    override fun createBinding(parent: ViewGroup): MedicalCategoryItemBinding {
        val binding = DataBindingUtil
                .inflate<MedicalCategoryItemBinding>(
                        LayoutInflater.from(parent.context),
                        R.layout.medical_category_item,
                        parent,
                        false,
                        dataBindingComponent
                )
        binding.root.setOnClickListener {
            binding.category?.let {
                callback?.invoke(it)
            }
        }

        val layoutParams = ConstraintLayout.LayoutParams(DisplayMetrics.width /2,DisplayMetrics.width /2)
        binding.root.layoutParams = layoutParams
        return binding
    }

    override fun bind(binding: MedicalCategoryItemBinding, item: MedicalCategoryUI) {
        binding.category = item
    }
}