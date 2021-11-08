package com.neqabty.presentation.ui.questionnaires

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.neqabty.R
import com.neqabty.databinding.QuestionnaireQuestionFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.util.autoCleared

class QuestionnaireQuestionFragment : DialogFragment(), Injectable {
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)
    var binding by autoCleared<QuestionnaireQuestionFragmentBinding>()

    lateinit var question: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.questionnaire_question_fragment,
                container,
                false,
                dataBindingComponent
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initializeViews()

    }

    private fun initializeViews() {
        val bundle = this.arguments
        bundle?.let { question = it.getString("question")!! }
        binding.webView.settings.loadsImagesAutomatically = true
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        val justify = "<html><body style='direction:rtl;text-align:justify;'>$question</body></html>"
        binding.webView.loadDataWithBaseURL(null, justify, "text/html; charset=utf-8", "UTF-8", null)


        binding.webView2.settings.loadsImagesAutomatically = true
        binding.webView2.settings.javaScriptEnabled = true
        binding.webView2.isLongClickable = true
        binding.webView2.setOnLongClickListener { return@setOnLongClickListener true }
        binding.webView2.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        binding.webView2.loadDataWithBaseURL(null, justify, "text/html; charset=utf-8", "UTF-8", null)


        binding.bClose.setOnClickListener { this.dismiss() }

    }
    //region

// endregion

    fun navController() = findNavController()
}
