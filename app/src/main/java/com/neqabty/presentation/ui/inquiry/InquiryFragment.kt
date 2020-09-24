package com.neqabty.presentation.ui.inquiry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.neqabty.R
import com.neqabty.databinding.InquiryFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.entities.ServiceTypeUI
import com.neqabty.presentation.entities.ServiceUI
import com.neqabty.presentation.util.autoCleared
import kotlinx.android.synthetic.main.inquiry_fragment.*
import javax.inject.Inject

class InquiryFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<InquiryFragmentBinding>()

    lateinit var inquiryViewModel: InquiryViewModel

    var serviceTypesResultList: List<ServiceTypeUI>? = mutableListOf()
    var servicesResultList: List<ServiceUI>? = mutableListOf()
    var serviceTypeID: Int = 0
    var serviceID: Int = 0
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

        inquiryViewModel.viewState.observe(this.requireActivity(), Observer {
            if (it != null) handleViewState(it)
        })
        inquiryViewModel.errorState.observe(this, Observer { _ ->
            showConnectionAlert(requireContext(), retryCallback = {
                llSuperProgressbar.visibility = View.VISIBLE
                inquiryViewModel.getAllServiceTypes()
            }, cancelCallback = {
                navController().navigateUp()
            })
        })
        inquiryViewModel.getAllServiceTypes()
    }

    fun initializeViews() {
//        if (!PreferencesHelper(requireContext()).user.equals("null"))
//            binding.edMemberNumber.setText(PreferencesHelper(requireContext()).user)
        renderServices()
        llContent.visibility = View.VISIBLE
        bSend.setOnClickListener {
            if (isDataValid(binding.edMemberNumber.text.toString())) {
                inquiryViewModel.paymentInquiry(binding.edMemberNumber.text.toString(), serviceID.toString())
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
        } else if (!state.isLoading && state.member != null) {
//            PreferencesHelper(requireContext()).user = state.member?.engineerID.toString()
//            if (state?.member?.code == 0 || state.member?.code == 1) {
//                navController().navigate(
//                        InquiryFragmentDirections.inquiryDetails(state.member!!)
//                )
//            } else {
//                showAlert(state?.member?.message!!)
//            }
            if (state.member?.msg == "") {
                state.member?.engineerNumber = edMemberNumber.text.toString()
                navController().navigate(
                        InquiryFragmentDirections.openInquiryDetails(0, spService.selectedItem.toString(), state.member!!, serviceID.toString())
                )
            } else {
                showAlert(state.member?.msg as String)
            }
            state.member = null
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
