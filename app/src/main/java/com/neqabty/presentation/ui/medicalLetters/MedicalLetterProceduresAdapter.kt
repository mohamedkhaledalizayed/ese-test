package com.neqabty.presentation.ui.medicalLetters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.MedicalLetterProcedureItemBinding
import com.neqabty.presentation.entities.MedicalLetterUI
import com.neqabty.ui.presentation.common.DataBoundListAdapter
import com.neqabty.ui.presentation.common.DataBoundViewHolder

class MedicalLetterProceduresAdapter(
    private val dataBindingComponent: DataBindingComponent,
    appExecutors: AppExecutors,
    private val callback: ((MedicalLetterUI.LetterProcedureItem) -> Unit)?
) : DataBoundListAdapter<MedicalLetterUI.LetterProcedureItem, MedicalLetterProcedureItemBinding>(
        appExecutors = appExecutors,
        diffCallback = object : DiffUtil.ItemCallback<MedicalLetterUI.LetterProcedureItem>() {
            override fun areItemsTheSame(oldItem: MedicalLetterUI.LetterProcedureItem, newItem: MedicalLetterUI.LetterProcedureItem): Boolean {
                return oldItem.letterProcedureName == newItem.letterProcedureName
            }

            override fun areContentsTheSame(oldItem: MedicalLetterUI.LetterProcedureItem, newItem: MedicalLetterUI.LetterProcedureItem): Boolean {
                return oldItem.letterProcedureName == newItem.letterProcedureName
            }
        }
) {

    override fun createBinding(parent: ViewGroup): MedicalLetterProcedureItemBinding {
        val binding = DataBindingUtil
                .inflate<MedicalLetterProcedureItemBinding>(
                        LayoutInflater.from(parent.context),
                        R.layout.medical_letter_procedure_item,
                        parent,
                        false,
                        dataBindingComponent
                )
        binding.root.setOnClickListener {
            binding.letterProcedure?.let {
                callback?.invoke(it)
            }
        }
        return binding
    }

    override fun bind(binding: MedicalLetterProcedureItemBinding, item: MedicalLetterUI.LetterProcedureItem) {
        binding.letterProcedure = item
    }

    override fun onBindViewHolder(holder: DataBoundViewHolder<MedicalLetterProcedureItemBinding>, position: Int) {
        super.onBindViewHolder(holder, position)
        if(position == 0){
            holder.binding.lineView.visibility = View.INVISIBLE
        }
    }
}
