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
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
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
//        binding.tvDesc.movementMethod = LinkMovementMethod.getInstance()
//        binding.tvDesc.text = Html.fromHtml(questionnaireUI.question)

        val justify = "<html><body style='text-align:justify;'>${questionnaireUI.question}</body></html>"
        binding.webView.loadDataWithBaseURL(null, justify, "text/html; charset=utf-8", "UTF-8", null)

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
                showStatistics()
            else
                initializeViews()
        }
    }

    private fun showStatistics(){
        binding.svContent.visibility = View.GONE
        binding.svResult.visibility = View.VISIBLE

        binding.bDetails.setOnClickListener {
            val fragmentManager = this@QuestionnaireFragment.fragmentManager
            val questionnaireQuestionFragment = QuestionnaireQuestionFragment()
            val bundle = Bundle()
            bundle.putString("question", questionnaireUI.question)
            questionnaireQuestionFragment.arguments = bundle
            questionnaireQuestionFragment.setTargetFragment(this, 255)
            questionnaireQuestionFragment.show(requireFragmentManager(), "question")
        }

        configurePieChart()
        loadPieChartData(questionnaireUI.total_votings, questionnaireUI.answers!!)

    }
    private fun configurePieChart() {
        binding.pcStatsChart.isDrawHoleEnabled = true
        binding.pcStatsChart.isClickable = false
        binding.pcStatsChart.setTouchEnabled(false)
        binding.pcStatsChart.setUsePercentValues(false)
        binding.pcStatsChart.setDrawEntryLabels(false)
        binding.pcStatsChart.setDrawCenterText(false)
        binding.pcStatsChart.setDrawRoundedSlices(false)
        binding.pcStatsChart.setDrawMarkers(false)
        binding.pcStatsChart.description.isEnabled = false

        val l: Legend = binding.pcStatsChart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
        l.isEnabled = false
        l.textSize = 20f
    }

    private fun loadPieChartData(totalCount: Int, answers: List<QuestionnaireUI.Answer>) {
        val entries: ArrayList<PieEntry> = ArrayList()
        var max = 0f
        var highestCandidate = ""
        for (item in answers) {
            if ((item.answer_count!!.toFloat() / totalCount.toFloat()) > max) {
                max = (item.answer_count!!.toFloat() / totalCount.toFloat())
                highestCandidate = Math.round(max * 100).toString() + "%" + "\n" + item.answer
            }
            entries.add(
                    PieEntry(
                            (item.answer_count!!.toFloat() / totalCount.toFloat()),
                            item.answer_count!!
                    )
            )
        }
        binding.tvResult.text = highestCandidate
        binding.pcStatsChart.centerText = highestCandidate

        val colors: ArrayList<Int> = ArrayList()
        var colorIndex = 0

        for (index in answers.indices){
            var color = ContextCompat.getColor(requireContext(), R.color.stats_green)
            when (colorIndex) {
                0 -> {
                    color = ContextCompat.getColor(requireContext(), R.color.stats_green)
                    colorIndex++
                }
                1 -> {
                    color = ContextCompat.getColor(requireContext(), R.color.stats_blue)
                    colorIndex++
                }
                2 -> {
                    color = ContextCompat.getColor(requireContext(), R.color.stats_orange)
                    colorIndex++
                }
                3 -> {
                    color = ContextCompat.getColor(requireContext(), R.color.stats_yellow)
                    colorIndex = 0
                }
            }
            colors.add(color)
            answers.get(index).color = color
        }

        val dataSet = PieDataSet(entries, "results")
        dataSet.colors = colors

        val data = PieData(dataSet)
        data.setDrawValues(false)
        binding.pcStatsChart.data = data
        binding.pcStatsChart.invalidate()
        binding.pcStatsChart.animateY(1400, Easing.EaseInOutQuad)

        binding.rvStats.adapter =
                QuestionnaireStatsAdapter(dataBindingComponent , appExecutors){}
        (binding.rvStats.adapter as QuestionnaireStatsAdapter).submitList(questionnaireUI.answers)
    }

    //region

// endregion

    fun navController() = findNavController()
}
