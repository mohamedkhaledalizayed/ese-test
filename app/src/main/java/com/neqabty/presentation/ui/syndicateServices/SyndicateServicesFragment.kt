package com.neqabty.presentation.ui.syndicateServices

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
import com.neqabty.presentation.entities.SyndicateServicesPaymentUI
import com.neqabty.presentation.entities.SyndicateServicesUI
import com.neqabty.presentation.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.inquiry_details_fragment.llContent
import kotlinx.android.synthetic.main.syndicate_services_fragment.*

@AndroidEntryPoint
class SyndicateServicesFragment : BaseFragment() {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<SyndicateServicesFragmentBinding>()

    private val syndicateServicesViewModel: SyndicateServicesViewModel by viewModels()

    var syndicateServicesResultList: List<SyndicateServicesUI.ServiceType>? = mutableListOf()
    var servicesResultList: List<SyndicateServicesUI.Service>? = mutableListOf()
    var syndicateServicesID: Int = 0
    var serviceID: Int = 0

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
            syndicateServicesViewModel.inquireSyndicateServicesPayment(sharedPref.mobile, sharedPref.user, sharedPref.name, (binding.spService.selectedItem as SyndicateServicesUI.Service).id, -1, "type", -1, "address", "01111111111")
        }
    }

    private fun handleViewState(state: SyndicateServicesViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        activity?.invalidateOptionsMenu()
        if(state.syndicateServicesPaymentUI != null){
            navController().navigate(
                SyndicateServicesFragmentDirections.openSyndicateServicesDetails(spService.selectedItem.toString(), serviceID, (spService.selectedItem as SyndicateServicesUI.Service).price!!, state.syndicateServicesPaymentUI as SyndicateServicesPaymentUI)
            )
            return
        }else if (llContent.visibility == View.INVISIBLE && state.serviceTypes != null) {
            syndicateServicesResultList = state.serviceTypes
            servicesResultList = state.services
            renderSyndicateServices()
            initializeViews()
            state.serviceTypes = null
            return
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
