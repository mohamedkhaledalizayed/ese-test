package com.neqabty.presentation.ui.engineeringRecordsInquiry

import androidx.lifecycle.Observer
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.neqabty.R
import com.neqabty.databinding.EngineeringRecordsInquiryFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EngineeringRecordsInquiryFragment : BaseFragment() {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<EngineeringRecordsInquiryFragmentBinding>()

    private val engineeringRecordsInquiryViewModel: EngineeringRecordsInquiryViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.engineering_records_inquiry_fragment,
                container,
                false,
                dataBindingComponent
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        engineeringRecordsInquiryViewModel.viewState.observe(this.requireActivity(), Observer {
            if (it != null) handleViewState(it)
        })
        engineeringRecordsInquiryViewModel.errorState.observe(this, Observer { error ->
            showConnectionAlert(requireContext(), retryCallback = {
                llSuperProgressbar.visibility = View.VISIBLE
                engineeringRecordsInquiryViewModel.sendEngineeringRecordsInquiry(binding.edMemberNumber.text.toString())
            }, cancelCallback = {
                navController().navigateUp()
            }, message = error?.message)
        })
        initializeViews()
    }

    fun initializeViews() {
        if (!sharedPref.user.equals("null"))
            binding.edMemberNumber.setText(sharedPref.user)

        binding.bSend.setOnClickListener {
            if (isDataValid(binding.edMemberNumber.text.toString())) {
                sharedPref.user = binding.edMemberNumber.text.toString()
                engineeringRecordsInquiryViewModel.sendEngineeringRecordsInquiry(binding.edMemberNumber.text.toString())
            }
        }
    }

    private fun handleViewState(state: EngineeringRecordsInquiryViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
//        if (!state.isLoading && state.memberItem != null) {
//            state.memberItem?.registryDataID = edMemberNumber.text.toString()
//                when(state.memberItem?.statusCode){
//                    0 ->  navController().navigate(
//                            EngineeringRecordsInquiryFragmentDirections.engineeringRecordsDetails(state.memberItem!!))
//                    else ->  showAlert(state.memberItem?.msg ?: getString(R.string.user_not_allowed))
//                }//TODO
//                state.memberItem = null
//        }
    }

    //region
    private fun isDataValid(number: String): Boolean {
        return if (number.isBlank()) {
            showAlert(getString(R.string.invalid_data))
            false
        } else if (number.length != 7 && number.length != 8) {
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
