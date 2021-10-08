package com.neqabty.presentation.ui.questionnaires

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.QuestionnaireStatItemBinding
import com.neqabty.presentation.entities.QuestionnaireUI
import com.neqabty.ui.presentation.common.DataBoundListAdapter

class QuestionnaireStatsAdapter(
    private val dataBindingComponent: DataBindingComponent,
    appExecutors: AppExecutors,
    private val callback: ((QuestionnaireUI.Answer) -> Unit)?
) : DataBoundListAdapter<QuestionnaireUI.Answer, QuestionnaireStatItemBinding>(
        appExecutors = appExecutors,
        diffCallback = object : DiffUtil.ItemCallback<QuestionnaireUI.Answer>() {
            override fun areItemsTheSame(oldItem: QuestionnaireUI.Answer, newItem: QuestionnaireUI.Answer): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: QuestionnaireUI.Answer, newItem: QuestionnaireUI.Answer): Boolean {
                return oldItem.id == newItem.id
            }
        }
) {

    override fun createBinding(parent: ViewGroup): QuestionnaireStatItemBinding {
        val binding = DataBindingUtil
                .inflate<QuestionnaireStatItemBinding>(
                        LayoutInflater.from(parent.context),
                        R.layout.questionnaire_stat_item,
                        parent,
                        false,
                        dataBindingComponent
                )
        binding.root.setOnClickListener {
        }

        return binding
    }

    override fun bind(binding: QuestionnaireStatItemBinding, item: QuestionnaireUI.Answer) {
        val percentage = (item.answer_count.toDouble() / item.total_count) * 100
        binding.tvName.setTextColor(item.color)
        binding.tvPercentage.setTextColor(item.color)
        binding.progressbar.progressDrawable.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(item.color, BlendModeCompat.SRC_IN)
        binding.tvName.text = item.answer
        binding.tvPercentage.text = Math.round(percentage).toString() + "%"
        binding.progressbar.progress = percentage.toInt()
    }
}
