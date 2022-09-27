package com.neqabty.presentation.ui.claiming

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.neqabty.R
import com.neqabty.databinding.Claiming2FragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.entities.ProviderTypeUI
import com.neqabty.presentation.entities.ProviderUI
import com.neqabty.presentation.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.claiming2_fragment.*

@AndroidEntryPoint
class ClaimingStep2Fragment : BaseFragment() {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<Claiming2FragmentBinding>()

    private val claimingViewModel: ClaimingViewModel by viewModels()
    var providersTypesResultList: MutableList<ProviderTypeUI>? = mutableListOf()
    var providersResultList: MutableList<ProviderUI>? = mutableListOf()

    var providerTypeID: Int = 0
    var providerID: Int = 0

    var isProvidersRequested = false
    lateinit var pager: ViewPager
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.claiming2_fragment,
                container,
                false,
                dataBindingComponent
        )
        pager = container as ViewPager
        return binding.root
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            initializeViews()
        }
    }

    fun initializeViews() {
        hideKeyboard()
        providersTypesResultList?.clear()
        providersResultList?.clear()

        claimingViewModel.viewState.value = claimingViewModel.viewState.value?.copy(providerTypes = null, providers = null)

        claimingViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        claimingViewModel.errorState.observe(this, Observer { error ->
            showConnectionAlert(requireContext(), retryCallback = {
                llSuperProgressbar.visibility = View.VISIBLE
                loadProviders()
            }, cancelCallback = {
                dialog?.dismiss()
            }, message = error?.message)
        })

        binding.edNumber.setText(sharedPref.user)
        binding.edCardNumber.setText(ClaimingData.cardId.toString())
        binding.bPrev.setOnClickListener {
            pager.setCurrentItem(0, true)
        }
        binding.bNext.setOnClickListener {
            if (isDataValid(spProvider.selectedItem)) {
                ClaimingData.providerTypeId = (spProviderType.selectedItem as ProviderTypeUI).id
                ClaimingData.providerId = (spProvider.selectedItem as ProviderUI).id
                ClaimingData.providerName = (spProvider.selectedItem as ProviderUI).toString()
                pager.setCurrentItem(2, true)
            }
        }
        loadProviders()
    }

    private fun handleViewState(state: ClaimingViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE

        state.providerTypes?.let {
            providersTypesResultList = it.toMutableList()
        }
        state.providers?.let {
            if (it.isEmpty()) {
                providersResultList?.clear()
                providersResultList!!.add(ProviderUI(0, getString(R.string.no_data_found), "", "", "", "", "", "", "", "", "", "", ""))
            } else
                providersResultList = it.toMutableList()
        }
        if (isProvidersRequested && !state.isLoading) {
            isProvidersRequested = false
            renderProviders()
            binding.svContent.visibility = if (state.isLoading) View.GONE else View.VISIBLE
        } else if (state.providerTypes != null && providersResultList?.size == 0 && !state.isLoading) {
            initializeSpinners()
        }
    }

    fun loadProviders() {
        llSuperProgressbar.visibility = View.VISIBLE
        claimingViewModel.getProviderTypes(ClaimingData.governId.toString(), ClaimingData.areaId.toString())
    }
//region

    fun initializeSpinners() {
        renderProvidersTypes()
    }

    fun renderProvidersTypes() {
        binding.spProviderType.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, providersTypesResultList!!)
        binding.spProviderType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                providerTypeID = (parent.getItemAtPosition(position) as ProviderTypeUI).id
                getProviders(providerTypeID)
            }
        }
        binding.spProviderType.setSelection(0)
    }

    fun renderProviders() {
        binding.spProvider.adapter = ProvidersSpinnerAdapter(requireContext(), providersResultList!!)
        binding.spProvider.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            }
        }
    }

    fun getProviders(providerTypeID: Int) {
        isProvidersRequested = true
        claimingViewModel.getProvidersByType(providerTypeID.toString(), ClaimingData.governId.toString(), ClaimingData.areaId.toString(), ClaimingData.searchProviderName)
    }

    private fun isDataValid(doctor: Any?): Boolean {
        return if (doctor != null && doctor.toString() != getString(R.string.no_data_found))
            true
        else {
            showInvalidDataAlert()
            false
        }
    }

    private fun showInvalidDataAlert() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.alert_title))
        builder.setMessage(getString(R.string.invalid_data))
        builder.setPositiveButton(getString(R.string.ok_btn)) { dialog, _ ->
            dialog.dismiss()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    // endregion
    fun navController() = findNavController()
}
