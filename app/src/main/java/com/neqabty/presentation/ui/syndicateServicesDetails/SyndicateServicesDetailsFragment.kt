package com.neqabty.presentation.ui.syndicateServicesDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.neqabty.R
import com.neqabty.databinding.SyndicateServicesDetailsFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.syndicate_services_details_fragment.*

@AndroidEntryPoint
class SyndicateServicesDetailsFragment : BaseFragment() {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<SyndicateServicesDetailsFragmentBinding>()

    private val syndicateServicesDetailsViewModel: SyndicateServicesDetailsViewModel by viewModels()
    lateinit var params: SyndicateServicesDetailsFragmentArgs

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.syndicate_services_details_fragment,
            container,
            false,
            dataBindingComponent
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        syndicateServicesDetailsViewModel.viewState.observe(this.requireActivity(), Observer {
            if (it != null) handleViewState(it)
        })
        syndicateServicesDetailsViewModel.errorState.observe(this, Observer { error ->
            showConnectionAlert(requireContext(), retryCallback = {
                llSuperProgressbar.visibility = View.VISIBLE
//                syndicateServicesDetailsViewModel.getSyndicateServicesDetails(sharedPref.user)
            }, cancelCallback = {
                navController().navigateUp()
            }, message = error?.message)
        })
        initializeViews()
    }

    fun initializeViews() {
        params = SyndicateServicesDetailsFragmentArgs.fromBundle(arguments!!)
        binding.title = params.title
        binding.name = sharedPref.name
        binding.number = sharedPref.user
//        binding.newAmount = sharedPref.user

        llContent.visibility = View.VISIBLE
        bPay.setOnClickListener {
//            syndicateServicesDetailsViewModel.paymentSyndicateServicesDetails(sharedPref.mobile, binding.edMemberNumber.text.toString(), serviceID)
        }
    }

    private fun handleViewState(state: SyndicateServicesDetailsViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        activity?.invalidateOptionsMenu()
//        if (llContent.visibility == View.INVISIBLE && state.serviceTypes != null) {
//            syndicateServicesDetailsResultList = state.serviceTypes
//            servicesResultList = state.services
//            renderSyndicateServicesDetails()
//            initializeViews()
//            state.serviceTypes = null
//            return
//        } else if (!state.isLoading && state.renewalPayment != null) {
//            if((state.renewalPayment as RenewalPaymentUI).resultType == "-2")
//                showAlert((state.renewalPayment as RenewalPaymentUI).msg)
//            else if((state.renewalPayment as RenewalPaymentUI).resultType == "-1")
//                showAlert((state.renewalPayment as RenewalPaymentUI).msg)
//            else if((state.renewalPayment as RenewalPaymentUI).resultType == "-3")
//                showAlert((state.renewalPayment as RenewalPaymentUI).msg)
//            else if((state.renewalPayment as RenewalPaymentUI).paymentItem != null)
//                navController().navigate(
//                    SyndicateServicesDetailsFragmentDirections.openInquiryDetails(edMemberNumber.text.toString(),0, spService.selectedItem.toString(), state.renewalPayment as RenewalPaymentUI, serviceID)
//                )
//            else
//                showAlert(getString(R.string.error_msg))
//            state.renewalPayment = null
//        }
    }
    //region
// endregion

    fun navController() = findNavController()
}
