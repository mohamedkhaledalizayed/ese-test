package com.neqabty.presentation.ui.inquiry

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.navigation.fragment.findNavController
import com.neqabty.R
import com.neqabty.databinding.InquiryFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.util.autoCleared

import javax.inject.Inject

class InquiryFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<InquiryFragmentBinding>()

    lateinit var inquiryViewModel: InquiryViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.inquiry_fragment,
                container,
                false,
                dataBindingComponent
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        inquiryViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(InquiryViewModel::class.java)

        inquiryViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        inquiryViewModel.errorState.observe(this, Observer { _ ->
            showConnectionAlert(requireContext(), retryCallback = {
                llSuperProgressbar.visibility = View.VISIBLE
                inquiryViewModel.validateUser(binding.edMemberNumber.text.toString())
            }, cancelCallback = {
                navController().navigateUp()
            })
        })
        initializeViews()
    }

    fun initializeViews() {
//        if (!PreferencesHelper(requireContext()).user.equals("null"))
//            binding.edMemberNumber.setText(PreferencesHelper(requireContext()).user)

        binding.bSend.setOnClickListener {
            if (isDataValid(binding.edMemberNumber.text.toString())) {
                inquiryViewModel.validateUser(binding.edMemberNumber.text.toString())
            }
        }
    }

    private fun handleViewState(state: InquiryViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        activity?.invalidateOptionsMenu()
        if (!state.isLoading && state.member != null) {
//            PreferencesHelper(requireContext()).user = state.member?.engineerID.toString()
//            if (state?.member?.code == 0 || state.member?.code == 1) {
//                navController().navigate(
//                        InquiryFragmentDirections.inquiryDetails(state.member!!)
//                )
//            } else {
//                showAlert(state?.member?.message!!)
//            }
            if (state?.member?.amount != 0) {
                navController().navigate(
                        InquiryFragmentDirections.inquiryDetails(state.member!!)
                )
            } else {
                showAlert(getString(R.string.user_not_allowed))
            }
            state.member = null
        }
    }

    //region
    private fun isDataValid(number: String): Boolean {
        return if (number.isBlank()) {
            showAlert(getString(R.string.invalid_data))
            false
        } else if (number.length > 7) {
            showAlert(getString(R.string.invalid_number))
            false
        } else {
            true
        }
    }

    private fun showAlert(msg: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.alert_title))
        builder.setMessage(msg)
        builder.setPositiveButton(getString(R.string.ok_btn)) { dialog, which ->
            dialog.dismiss()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

// endregion

    fun navController() = findNavController()
}
