package com.neqabty.presentation.ui.medicalLetters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.MedicalLetterItemBinding
import com.neqabty.presentation.entities.MedicalLetterUI
import com.neqabty.ui.presentation.common.DataBoundListAdapter

class MedicalLettersAdapter(
    private val dataBindingComponent: DataBindingComponent,
    private val appExecutors: AppExecutors,
    private val callback: ((MedicalLetterUI.LetterItem) -> Unit)?
) : DataBoundListAdapter<MedicalLetterUI.LetterItem, MedicalLetterItemBinding>(
        appExecutors = appExecutors,
        diffCallback = object : DiffUtil.ItemCallback<MedicalLetterUI.LetterItem>() {
            override fun areItemsTheSame(oldItem: MedicalLetterUI.LetterItem, newItem: MedicalLetterUI.LetterItem): Boolean {
                return oldItem.letterDate == newItem.letterDate
            }

            override fun areContentsTheSame(oldItem: MedicalLetterUI.LetterItem, newItem: MedicalLetterUI.LetterItem): Boolean {
                return oldItem.letterDate == newItem.letterDate
            }
        }
) {

    override fun createBinding(parent: ViewGroup): MedicalLetterItemBinding {
        val binding = DataBindingUtil
                .inflate<MedicalLetterItemBinding>(
                        LayoutInflater.from(parent.context),
                        R.layout.medical_letter_item,
                        parent,
                        false,
                        dataBindingComponent
                )
        binding.root.setOnClickListener {
            binding.letter?.let {
                callback?.invoke(it)
            }
        }
        binding.rvLetterProcedures.adapter = MedicalLetterProceduresAdapter(dataBindingComponent, appExecutors, {})
        return binding
    }

    override fun bind(binding: MedicalLetterItemBinding, item: MedicalLetterUI.LetterItem) {
        binding.letter = item
        (binding.rvLetterProcedures.adapter as MedicalLetterProceduresAdapter).submitList(item.letterProcedures)
    }
}
