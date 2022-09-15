package com.neqabty.presentation.ui.syndicateServices

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
import com.neqabty.databinding.SyndicateServicesFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.common.Constants
import com.neqabty.presentation.entities.MedicalRenewalPaymentUI
import com.neqabty.presentation.entities.SyndicateServicesUI
import com.neqabty.presentation.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.inquiry_details_fragment.llContent
import kotlinx.android.synthetic.main.inquiry_fragment.*

@AndroidEntryPoint
class SyndicateServicesFragment : BaseFragment() {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<SyndicateServicesFragmentBinding>()

    private val syndicateServicesViewModel: SyndicateServicesViewModel by viewModels()

    var syndicateServicesResultList: List<SyndicateServicesUI.ServiceType>? = mutableListOf()
    var servicesResultList: List<SyndicateServicesUI.Service>? = mutableListOf()
    var syndicateServicesID: Int = 0
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
            R.layout.syndicate_services_fragment,
            container,
            false,
            dataBindingComponent
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        showBannerAd(Constants.AD_PAYMENTS, binding.ivBanner)

        syndicateServicesViewModel.viewState.observe(this.requireActivity(), Observer {
            if (it != null) handleViewState(it)
        })
        syndicateServicesViewModel.errorState.observe(this, Observer { error ->
            showConnectionAlert(requireContext(), retryCallback = {
                llSuperProgressbar.visibility = View.VISIBLE
                syndicateServicesViewModel.getSyndicateServices(sharedPref.user)
            }, cancelCallback = {
                navController().navigateUp()
            }, message = error?.message)
        })
        syndicateServicesViewModel.getSyndicateServices(sharedPref.user)
    }

    fun initializeViews() {
        if (!sharedPref.user.isNullOrEmpty())
            binding.edMemberNumber.setText(sharedPref.user)

        renderServices()
        llContent.visibility = View.VISIBLE
        bSend.setOnClickListener {
//            if (isDataValid(binding.edMemberNumber.text.toString())) {
            syndicateServicesViewModel.paymentSyndicateServices(sharedPref.mobile, binding.edMemberNumber.text.toString(), serviceID.toString())
//            }
        }
    }

    private fun handleViewState(state: SyndicateServicesViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        activity?.invalidateOptionsMenu()
        if (llContent.visibility == View.INVISIBLE && state.serviceTypes != null) {
            syndicateServicesResultList = state.serviceTypes
            servicesResultList = state.services
            renderSyndicateServices()
            initializeViews()
            state.serviceTypes = null
            return
        } else if (!state.isLoading && state.medicalRenewalPayment != null) {
            if((state.medicalRenewalPayment as MedicalRenewalPaymentUI).resultType == "-2")
                showAlert((state.medicalRenewalPayment as MedicalRenewalPaymentUI).msg)
            else if((state.medicalRenewalPayment as MedicalRenewalPaymentUI).resultType == "-1")
                showAlert((state.medicalRenewalPayment as MedicalRenewalPaymentUI).msg)
            else if((state.medicalRenewalPayment as MedicalRenewalPaymentUI).resultType == "-3")
                showAlert((state.medicalRenewalPayment as MedicalRenewalPaymentUI).msg)
            else if((state.medicalRenewalPayment as MedicalRenewalPaymentUI).paymentItem != null)
                navController().navigate(
                    SyndicateServicesFragmentDirections.openInquiryDetails(edMemberNumber.text.toString(),0, spService.selectedItem.toString(), state.medicalRenewalPayment as MedicalRenewalPaymentUI, serviceID.toString())
                )
            else
                showAlert(getString(R.string.error_msg))
            state.medicalRenewalPayment = null
        }
    }

    fun renderSyndicateServices() {
        syndicateServicesResultList = syndicateServicesResultList?.filter { it.id == 4 || it.id == 6 }
        binding.spSyndicateServices.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, syndicateServicesResultList!!)
        binding.spSyndicateServices.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                syndicateServicesID = (parent.getItemAtPosition(position) as SyndicateServicesUI.ServiceType).id
                servicesResultList = syndicateServicesViewModel.viewState.value?.services?.filter { it.groupID == syndicateServicesID }
                renderServices()
            }
        }
        binding.spSyndicateServices.setSelection(0)
//        inquiryViewModel.getAllServices((spSyndicateServices.selectedItem as SyndicateServicesUI.SyndicateServices).id)
    }

    fun renderServices() {
        servicesResultList = servicesResultList?.filter { it.id == 1086 || it.id == 3090 || it.id == 6534 }
        binding.spService.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, servicesResultList!!)
        binding.spService.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                serviceID = (parent.getItemAtPosition(position) as SyndicateServicesUI.Service).id
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
