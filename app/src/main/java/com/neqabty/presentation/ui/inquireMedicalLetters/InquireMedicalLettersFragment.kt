package com.neqabty.presentation.ui.inquireMedicalLetters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.InquireMedicalLettersFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.entities.MedicalLetterUI
import com.neqabty.presentation.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.medical_letters_fragment.*
import javax.inject.Inject

@AndroidEntryPoint
class InquireMedicalLettersFragment : BaseFragment() {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<InquireMedicalLettersFragmentBinding>()

    private val inquireMedicalLettersViewModel: InquireMedicalLettersViewModel by viewModels()

    @Inject
    lateinit var appExecutors: AppExecutors

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.inquire_medical_letters_fragment,
                container,
                false,
                dataBindingComponent
        )

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        inquireMedicalLettersViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        inquireMedicalLettersViewModel.errorState.observe(this, Observer { error ->
            showConnectionAlert(requireContext(), retryCallback = {
                llSuperProgressbar.visibility = View.VISIBLE
            }, cancelCallback = {
                navController().navigateUp()
            }, message = error?.message)
        })
        initializeViews()
    }

    private fun handleViewState(state: InquireMedicalLettersViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        llContent.visibility = if (state.isLoading) View.INVISIBLE else View.VISIBLE

        if (state.medicalLetterItemUI?.id.isNullOrEmpty()){
            binding.tvNoDataFound.visibility = View.VISIBLE
            binding.llResult.visibility = View.GONE
        } else {
            binding.tvNoDataFound.visibility = View.GONE
            binding.llResult.visibility = View.VISIBLE
            binding.clResult.letter = state.medicalLetterItemUI!!
        }
    }

    fun initializeViews() {
        binding.bInquire.setOnClickListener{
            hideKeyboard()
            if(binding.edApprovalNumber.text.isNullOrBlank())
                showAlert(message = getString(R.string.invalid_data))
            else {
                llSuperProgressbar.visibility = View.VISIBLE
                binding.tvNoDataFound.visibility = View.GONE
                inquireMedicalLettersViewModel.getMedicalLetterByID(sharedPref.mobile, sharedPref.user, binding.edApprovalNumber.text.toString())
            }
        }
    }
//region

// endregion

    fun navController() = findNavController()
}
