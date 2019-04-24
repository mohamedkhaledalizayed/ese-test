package com.neqabty.presentation.ui.claiming

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.neqabty.R
import com.neqabty.databinding.Claiming2FragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.entities.ProviderTypeUI
import com.neqabty.presentation.entities.ProviderUI
import com.neqabty.presentation.util.PreferencesHelper
import com.neqabty.presentation.util.autoCleared
import com.neqabty.testing.OpenForTesting
import kotlinx.android.synthetic.main.claiming2_fragment.*
import javax.inject.Inject

@OpenForTesting
class ClaimingStep2Fragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<Claiming2FragmentBinding>()

    lateinit var claimingViewModel: ClaimingViewModel
    var providersTypesResultList: List<ProviderTypeUI>? = mutableListOf()
    var providersResultList: List<ProviderUI>? = null

    var providerTypeID: Int = 0
    var providerID: Int = 0

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
        if (isVisibleToUser)
            initializeViews()
    }

    fun initializeViews() {
        hideKeyboard()
        claimingViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(ClaimingViewModel::class.java)

        claimingViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        claimingViewModel.errorState.observe(this, Observer { _ ->
            showConnectionAlert(requireContext(), retryCallback = {
                binding.progressbar.visibility = View.VISIBLE
                claimingViewModel.getProviderTypes(ClaimingData.governId.toString(), ClaimingData.areaId.toString())
            }, cancelCallback = {
                navController().popBackStack()
                navController().navigate(R.id.homeFragment)
            })
        })

        binding.edNumber.setText(PreferencesHelper(requireContext()).user)

        binding.bNext.setOnClickListener {
            if (isDataValid(spProvider.selectedItem)) {
                ClaimingData.providerTypeId = (spProviderType.selectedItem as ProviderTypeUI).id
                ClaimingData.providerId = (spProvider.selectedItem as ProviderUI).id
                ClaimingData.providerName = (spProvider.selectedItem as ProviderUI).toString()
                pager.setCurrentItem(2, true)
            }
        }
        claimingViewModel.getProviderTypes(ClaimingData.governId.toString(), ClaimingData.areaId.toString())
    }

    private fun handleViewState(state: ClaimingViewState) {
        binding.progressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE

        state.providerTypes?.let {
            providersTypesResultList = it
        }
        state.providers?.let {
            providersResultList = it
        }
        if (state.providerTypes != null && providersResultList == null && !state.isLoading)
            initializeSpinners()
        else if (!state.isLoading) {
            renderProviders()
            binding.svContent.visibility = if (state.isLoading) View.GONE else View.VISIBLE
        }
    }

//region

    fun initializeSpinners() {
        renderProvidersTypes()
    }

    fun renderProvidersTypes() {
        binding.spProviderType.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, providersTypesResultList)
        binding.spProviderType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
//                if (providerTypeID.equals((parent.getItemAtPosition(position) as ProviderTypeUI).id.toString()!!))
//                    return

                providerTypeID = (parent.getItemAtPosition(position) as ProviderTypeUI).id
                getProviders(providerTypeID)
            }
        }
        binding.spProviderType.setSelection(0)
    }

    fun renderProviders() {
        binding.spProvider.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, providersResultList)
        binding.spProvider.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            }
        }
    }

    fun getProviders(providerTypeID: Int) {
        claimingViewModel.getProvidersByType(providerTypeID.toString(), ClaimingData.governId.toString(), ClaimingData.areaId.toString())
    }

    private fun isDataValid(doctor: Any?): Boolean {
        return if (doctor != null)
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

    fun hideKeyboard() {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.edNumber.windowToken, 0)
    }
// endregion
fun navController() = findNavController()
}
