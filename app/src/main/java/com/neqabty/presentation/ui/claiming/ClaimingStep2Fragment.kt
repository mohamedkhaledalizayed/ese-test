package com.neqabty.presentation.ui.claiming

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.neqabty.R
import com.neqabty.databinding.Claiming2FragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.entities.AreaUI
import com.neqabty.presentation.entities.ProviderTypeUI
import com.neqabty.presentation.entities.ProviderUI
import com.neqabty.presentation.util.autoCleared
import com.neqabty.testing.OpenForTesting
import kotlinx.android.synthetic.main.claiming2_fragment.*
import javax.inject.Inject
import com.neqabty.presentation.util.PreferencesHelper

@OpenForTesting
class ClaimingStep2Fragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<Claiming2FragmentBinding>()

    lateinit var claimingViewModel: ClaimingViewModel
    var areasResultList: List<AreaUI>? = mutableListOf()
    var providersTypesResultList: List<ProviderTypeUI>? = mutableListOf()
    var providersResultList: List<ProviderUI>? = mutableListOf()

    var providerTypeID: String = "1"
    var providerID: String = "1"
    var areaID: String = "1"

    lateinit var pager: ViewPager
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
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
        claimingViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(ClaimingViewModel::class.java)

        claimingViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        claimingViewModel.errorState.observe(this, Observer { _ ->
            showConnectionAlert(requireContext(),retryCallback =  {
                binding.progressbar.visibility = View.VISIBLE
                claimingViewModel.getAllContent2("1")
            }, cancelCallback = {
                navController().popBackStack()
                navController().navigate(R.id.homeFragment)
            })
        })

        binding.edNumber.setText(PreferencesHelper(requireContext()).user)
        binding.edDoctor.setText(ClaimingData.doctorName)

        binding.bNext.setOnClickListener {
            if (isDataValid(spProvider.selectedItem)) {
                ClaimingData.providerTypeId = (spProviderType.selectedItem as ProviderTypeUI).id
                ClaimingData.providerId = (spProvider.selectedItem as ProviderUI).id
                ClaimingData.providerName = (spProvider.selectedItem as ProviderUI).toString()
                pager.setCurrentItem(2, true)
            }
        }
        claimingViewModel.getAllContent2("1")
    }


    private fun handleViewState(state: ClaimingViewState) {
        binding.progressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        binding.svContent.visibility = if (state.isLoading) View.GONE else View.VISIBLE
        state.areas?.let {
            areasResultList = it
        }
        state.providerTypes?.let {
            providersTypesResultList = it
        }
        state.providers?.let {
            providersResultList = it
        }
        if (state.providers != null && state.areas != null && state.providerTypes != null) {
            initializeSpinners()
            state.areas = null
            state.providers = null
        } else if (state.providers != null) {
            renderProviders()
        }
    }

//region

    fun initializeSpinners() {
        renderAreas()
        renderProvidersTypes()

        renderProviders()
    }

    fun renderAreas() {
        binding.spArea.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, areasResultList)
        binding.spArea.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (areaID.equals((parent.getItemAtPosition(position) as AreaUI).id.toString()!!))
                    return
                areaID = (parent.getItemAtPosition(position) as AreaUI).id.toString()!!
                renderProviders()
            }
        }
    }

    fun renderProvidersTypes() {
        binding.spProviderType.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, providersTypesResultList)
        binding.spProviderType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (providerTypeID.equals((parent.getItemAtPosition(position) as ProviderTypeUI).id.toString()!!))
                    return

                providerTypeID = (parent.getItemAtPosition(position) as ProviderTypeUI).id.toString()!!
                getProvidersByType()
            }
        }
    }

    fun renderProviders() {
        var filteredProvidersList: List<ProviderUI>? = mutableListOf()

        filteredProvidersList = providersResultList?.filter {
            it.areaId.equals(areaID)
        }

        binding.spProvider.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, filteredProvidersList)
        binding.spProvider.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            }
        }
    }

    fun getProvidersByType() {
        claimingViewModel.getProvidersByType(providerTypeID)
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
// endregion
fun navController() = findNavController()

}
