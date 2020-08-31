package com.neqabty.presentation.ui.tripsReservation

import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.CompanionItemBinding
import com.neqabty.domain.entities.PersonEntity
import com.neqabty.ui.presentation.common.DataBoundListAdapter

class CompanionsAdapter(
    private val dataBindingComponent: DataBindingComponent,
    appExecutors: AppExecutors,
    private val callback: ((PersonEntity) -> Unit)?
) : DataBoundListAdapter<PersonEntity, CompanionItemBinding>(
        appExecutors = appExecutors,
        diffCallback = object : DiffUtil.ItemCallback<PersonEntity>() {
            override fun areItemsTheSame(oldItem: PersonEntity, newItem: PersonEntity): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: PersonEntity, newItem: PersonEntity): Boolean {
                return oldItem.name == newItem.name
            }
        }
) {

    override fun createBinding(parent: ViewGroup): CompanionItemBinding {
        val binding = DataBindingUtil
                .inflate<CompanionItemBinding>(
                        LayoutInflater.from(parent.context),
                        R.layout.companion_item,
                        parent,
                        false,
                        dataBindingComponent
                )
        binding.root.setOnClickListener {
            binding.companion?.let {
                callback?.invoke(it)
            }
        }
        return binding
    }

    override fun bind(binding: CompanionItemBinding, item: PersonEntity) {
        binding.companion = item
    }
}
