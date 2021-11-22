package com.neqabty.presentation.ui.chooseArea

import android.content.Context
import android.content.Intent
import android.net.Uri
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
import com.neqabty.databinding.ChooseAreaFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.common.Constants
import com.neqabty.presentation.entities.AreaUI
import com.neqabty.presentation.entities.GovernUI
import com.neqabty.presentation.entities.MedicalDirectoryLookupsUI
import com.neqabty.presentation.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ChooseAreaFragment : BaseFragment() {
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)
    var binding by autoCleared<ChooseAreaFragmentBinding>()

    private val chooseAreaViewModel: ChooseAreaViewModel by viewModels()

    @Inject
    lateinit var appExecutors: AppExecutors

    var governsResultList: List<MedicalDirectoryLookupsUI.Govern>? = mutableListOf()
    var areasResultList: List<MedicalDirectoryLookupsUI.Area>? = mutableListOf()
    var governID: Int = 0
    var areaID: Int = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        showAds(Constants.AD_MEDICAL_DIRECTORY)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.choose_area_fragment,
                container,
                false,
                dataBindingComponent
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        showBannerAd(Constants.AD_MEDICAL_DIRECTORY, binding.ivBanner)

        chooseAreaViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        chooseAreaViewModel.errorState.observe(this, Observer { error ->
            showConnectionAlert(requireContext(), retryCallback = {
                llSuperProgressbar.visibility = View.VISIBLE
                chooseAreaViewModel.getAllContent1(sharedPref.mobile)
            }, cancelCallback = {
                navController().popBackStack()
                navController().navigate(R.id.homeFragment)
            }, message = error?.message)
        })
        chooseAreaViewModel.getAllContent1(sharedPref.mobile)
        initializeViews()
    }

    private fun initializeViews() {
        binding.bSubscriptionRules.setOnClickListener {
            val subscriptionIntent = Intent(Intent.ACTION_VIEW, Uri.parse(Constants.DNS + "/pdf/Medical.pdf"))
            startActivity(subscriptionIntent)
        }
        binding.bNext.setOnClickListener {
            navController().navigate(
                    ChooseAreaFragmentDirections.openMedicalMain(binding.edServiceProviderName.text.toString(), governID, areaID)
            )
        }
    }

    private fun handleViewState(state: ChooseAreaViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        if (state.governs != null && state.areas != null) {
            binding.llHolder.visibility = if (state.isLoading) View.GONE else View.VISIBLE
            state.governs?.let {
                governsResultList = it
            }
            state.areas?.let {
                areasResultList = it
            }
            if (state.governs != null && state.areas != null)
                initializeSpinners()
        }
    }

    fun initializeSpinners() {
        renderGoverns()
    }

    //region
    fun renderGoverns() {
        binding.spGovern.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, governsResultList!!)
        binding.spGovern.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                governID = (parent.getItemAtPosition(position) as MedicalDirectoryLookupsUI.Govern).id
                renderAreas()
            }
        }
        binding.spGovern.setSelection(0)
    }

    fun renderAreas() {
        var filteredAreasList: List<MedicalDirectoryLookupsUI.Area>? = mutableListOf()

        filteredAreasList = areasResultList?.filter {
            it.govId == governID
        }

        binding.spArea.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, filteredAreasList!!)
        binding.spArea.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                areaID = (parent.getItemAtPosition(position) as MedicalDirectoryLookupsUI.Area).id
            }
        }
        binding.spArea.setSelection(0)
    }

// endregion

    fun navController() = findNavController()
}
