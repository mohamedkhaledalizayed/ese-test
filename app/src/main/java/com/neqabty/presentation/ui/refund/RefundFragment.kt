package com.neqabty.presentation.ui.refund

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.RefundFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.entities.*
import com.neqabty.presentation.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import java.io.*
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class RefundFragment : BaseFragment() {
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)
    var binding by autoCleared<RefundFragmentBinding>()

    private val refundViewModel: RefundViewModel by viewModels()

    var liteFollowersListUI: List<LiteFollowersListUI>? = listOf<LiteFollowersListUI>()
    var governsResultList: List<GovernUI>? = mutableListOf()
    var areasResultList: List<AreaUI>? = mutableListOf()
    var providersTypesResultList: MutableList<ProviderTypeUI>? = mutableListOf()
    var providersResultList: MutableList<ProviderUI>? = mutableListOf()
    var governID: Int = 0
    var areaID: Int = 0
    var providerTypeID: Int = 0
    var providerID: Int = 0
    lateinit var selectedFollower: LiteFollowersListUI

    @Inject
    lateinit var appExecutors: AppExecutors

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.refund_fragment,
                container,
                false,
                dataBindingComponent
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        refundViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        refundViewModel.errorState.observe(this, Observer { error ->
            showConnectionAlert(requireContext(), retryCallback = {
                llSuperProgressbar.visibility = View.VISIBLE
                refundViewModel.getAllContent1(sharedPref.mobile, sharedPref.user)
            }, cancelCallback = {
                dialog?.dismiss()
            }, message = error?.message)
        })
        refundViewModel.getAllContent1(sharedPref.mobile, sharedPref.user)
    }

    private fun initializeViews() {
        initializeSpinners()
        binding.bNext.setOnClickListener{
            navController().navigate(RefundFragmentDirections.openRefundRequest(RefundRequest(sharedPref.name, sharedPref.mobile, sharedPref.user, binding.edCardNumber.text.toString(), "", (binding.spProvider.selectedItem as ProviderUI).branchProfileId.toString(), (binding.spProvider.selectedItem as ProviderUI).providerId.toString(), "4", sharedPref.token, listOf())))
        }
    }

    private fun handleViewState(state: RefundViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE

        state.providers?.let {
            if (it.isEmpty()) {
                providersResultList?.clear()
                providersResultList!!.add(ProviderUI(0, getString(R.string.no_data_found), "", "", "", "", "", "", "", "", "", "", "", "", ""))
            } else
                providersResultList = it.toMutableList()

            renderProviders()
            state.providers = null
            return
        }
        state.providerTypes?.let {
            providersTypesResultList = it.toMutableList()
            renderProvidersTypes()
            state.providerTypes = null
            return
        }

        if (state.liteFollowersListUI != null && state.governs != null && state.areas != null) {
                binding.llHolder.visibility = View.VISIBLE
                liteFollowersListUI = state.liteFollowersListUI
                governsResultList = state.governs
                areasResultList = state.areas
                initializeViews()

                state.liteFollowersListUI = null
                state.governs = null
                state.areas = null
        }
    }

    fun loadProviderTypes() {
        refundViewModel.getProviderTypes(governID.toString(), areaID.toString())
    }

    fun getProviders(providerTypeID: Int) {
        refundViewModel.getProvidersByType(providerTypeID.toString(), governID.toString(), areaID.toString(), "")
    }

    //region

    fun initializeSpinners() {
        renderFollowers()
        renderGoverns()
    }
    fun renderGoverns() {
        binding.spGovern.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, governsResultList!!)
        binding.spGovern.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                governID = (parent.getItemAtPosition(position) as GovernUI).id
                renderAreas()
            }
        }
        binding.spGovern.setSelection(0)
    }

    fun renderAreas() {
        var filteredAreasList: List<AreaUI>? = mutableListOf()

        filteredAreasList = areasResultList?.filter {
            it.govId == governID
        }

        binding.spArea.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, filteredAreasList!!)
        binding.spArea.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                areaID = (parent.getItemAtPosition(position) as AreaUI).id
                loadProviderTypes()
            }
        }
        binding.spArea.setSelection(0)
    }

    fun renderFollowers() {
        binding.spFollower.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, liteFollowersListUI!!)
        binding.spFollower.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedFollower = parent.getItemAtPosition(position) as LiteFollowersListUI
                binding.edCardNumber.setText(selectedFollower.id.toString())
            }
        }
        binding.spFollower.setSelection(0)
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
        binding.spProvider.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, providersResultList!!)
        binding.spProvider.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            }
        }
    }
// endregion

    fun navController() = findNavController()
}
