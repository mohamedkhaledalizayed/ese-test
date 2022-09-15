package com.neqabty.presentation.ui.inquiry

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.neqabty.R
import com.neqabty.databinding.InquiryFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.common.Constants
import com.neqabty.presentation.entities.RenewalPaymentUI
import com.neqabty.presentation.entities.ServiceTypeUI
import com.neqabty.presentation.entities.ServiceUI
import com.neqabty.presentation.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.inquiry_fragment.*

@AndroidEntryPoint
class InquiryFragment : BaseFragment() {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<InquiryFragmentBinding>()

    private val inquiryViewModel: InquiryViewModel by viewModels()

    var serviceTypesResultList: List<ServiceTypeUI>? = mutableListOf()
    var servicesResultList: List<ServiceUI>? = mutableListOf()
    var serviceTypeID: Int = 0
    var serviceID: Int = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        showAds(Constants.AD_PAYMENTS)
    }

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
        showBannerAd(Constants.AD_PAYMENTS, binding.ivBanner)

        inquiryViewModel.viewState.observe(this.requireActivity(), Observer {
            if (it != null) handleViewState(it)
        })
        inquiryViewModel.errorState.observe(this, Observer { error ->
            showConnectionAlert(requireContext(), retryCallback = {
                llSuperProgressbar.visibility = View.VISIBLE
                inquiryViewModel.getAllServiceTypes()
            }, cancelCallback = {
                navController().navigateUp()
            }, message = error?.message)
        })
        inquiryViewModel.getAllServiceTypes()
    }

    fun initializeViews() {
        if (!sharedPref.user.isNullOrEmpty())
            binding.edMemberNumber.setText(sharedPref.user)

        renderServices()
        llContent.visibility = View.VISIBLE
        bSend.setOnClickListener {
            if (isDataValid(binding.edMemberNumber.text.toString())) {
                inquiryViewModel.paymentInquiry(sharedPref.mobile, binding.edMemberNumber.text.toString(), serviceID)
            }
        }
    }

    private fun handleViewState(state: InquiryViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        activity?.invalidateOptionsMenu()
        if (state.services != null) {
            servicesResultList = state.services
            initializeViews()
            state.services = null
            return
        } else if (llContent.visibility == View.INVISIBLE && state.serviceTypes != null) {
            serviceTypesResultList = state.serviceTypes
            renderServiceTypes()
            state.serviceTypes = null
            return
        } else if (!state.isLoading && state.renewalPayment != null) {
            if((state.renewalPayment as RenewalPaymentUI).resultType == "-2")
                showAlert((state.renewalPayment as RenewalPaymentUI).msg)
            else if((state.renewalPayment as RenewalPaymentUI).resultType == "-1")
                showAlert((state.renewalPayment as RenewalPaymentUI).msg)
            else if((state.renewalPayment as RenewalPaymentUI).resultType == "-3")
                showAlert((state.renewalPayment as RenewalPaymentUI).msg)
            else if((state.renewalPayment as RenewalPaymentUI).paymentItem != null)
                navController().navigate(
                        InquiryFragmentDirections.openInquiryDetails(edMemberNumber.text.toString(),0, spService.selectedItem.toString(), state.renewalPayment as RenewalPaymentUI, serviceID)
                )
            else
                showAlert(getString(R.string.error_msg))
            state.renewalPayment = null
        }
    }

    fun renderServiceTypes() {
        binding.spServiceTypes.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, serviceTypesResultList!!)
        binding.spServiceTypes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                serviceTypeID = (parent.getItemAtPosition(position) as ServiceTypeUI).id
                llSuperProgressbar.visibility = View.VISIBLE
                inquiryViewModel.getAllServices(serviceTypeID)
            }
        }
        binding.spServiceTypes.setSelection(0)
        inquiryViewModel.getAllServices((spServiceTypes.selectedItem as ServiceTypeUI).id)
    }

    fun renderServices() {
        binding.spService.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, servicesResultList!!)
        binding.spService.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                serviceID = (parent.getItemAtPosition(position) as ServiceUI).id
            }
        }
        binding.spService.setSelection(0)
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
