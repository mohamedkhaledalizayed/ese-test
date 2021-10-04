package com.neqabty.presentation.ui.questionnaires

import android.content.Context
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.QuestionnaireFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.common.Constants
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.entities.QuestionnaireUI
import com.neqabty.presentation.util.PreferencesHelper
import com.neqabty.presentation.util.autoCleared
import javax.inject.Inject

class QuestionnaireFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)
    var binding by autoCleared<QuestionnaireFragmentBinding>()

    @Inject
    lateinit var questionnaireViewModel: QuestionnaireViewModel

    @Inject
    lateinit var appExecutors: AppExecutors

    lateinit var questionnaireUI: QuestionnaireUI
    override fun onAttach(context: Context) {
        super.onAttach(context)
        showAds(Constants.AD_QUESTIONNAIRE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.questionnaire_fragment,
                container,
                false,
                dataBindingComponent
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        questionnaireViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(QuestionnaireViewModel::class.java)

        questionnaireViewModel.viewState.observe(this.requireActivity(), Observer {
            if (it != null) handleViewState(it)
        })
        questionnaireViewModel.errorState.observe(this, Observer { error ->
            showConnectionAlert(requireContext(), retryCallback = {
                llSuperProgressbar.visibility = View.VISIBLE

                questionnaireViewModel.getQuestionnaire(PreferencesHelper(requireContext()).user)
            }, cancelCallback = {
                dialog?.dismiss()
            }, message = error?.message)
        })

        questionnaireViewModel.getQuestionnaire(PreferencesHelper(requireContext()).user)
    }

    private fun initializeViews() {
        binding.tvDesc.movementMethod = LinkMovementMethod.getInstance()
        binding.tvDesc.text = Html.fromHtml(questionnaireUI.question)

        for (i in questionnaireUI.answers!!.indices) {
            val radioButton = RadioButton(activity)
            radioButton.apply {
                text = questionnaireUI.answers!![i].answer
                textSize = 14f
                id = questionnaireUI.answers!![i].id
                gravity = Gravity.TOP
            }
            binding.rgAnswers.addView(radioButton)
            val v = View(activity)
            v.layoutParams = RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 15)
            binding.rgAnswers.addView(v)
        }

        binding.bSubmit.setOnClickListener {
            questionnaireViewModel.voteQuestionnaire(PreferencesHelper(requireContext()).user, questionnaireUI.id!!.toInt(), binding.rgAnswers.checkedRadioButtonId)
        }
    }

    private fun handleViewState(state: QuestionnaireViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        if (state.message.isNotBlank()) {
            showAlert(state.message){navController().navigateUp()}
            return
        }
        if (!state.isLoading && state.questionnaire != null) {
            binding.llHolder.visibility = if (state.isLoading) View.GONE else View.VISIBLE
            questionnaireUI = state.questionnaire!!
            if(questionnaireUI.is_voted)
                showAlert(getString(R.string.questionnaire_answered)){navController().navigateUp()}
            else
                initializeViews()
        }
    }

    //region

// endregion

    fun navController() = findNavController()
}
