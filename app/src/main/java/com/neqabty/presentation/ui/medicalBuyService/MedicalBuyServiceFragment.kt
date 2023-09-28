package com.neqabty.presentation.ui.medicalBuyService

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.MedicalBuyServiceFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.entities.ProviderTypeUI
import com.neqabty.presentation.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MedicalBuyServiceFragment : BaseFragment() {
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)
    var binding by autoCleared<MedicalBuyServiceFragmentBinding>()

    private val medicalBuyServiceViewModel: MedicalBuyServiceViewModel by viewModels()

    var providersTypesResultList: MutableList<ProviderTypeUI>? = mutableListOf()
    var providerTypeID: Int = 0

    @Inject
    lateinit var appExecutors: AppExecutors

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.medical_buy_service_fragment,
            container,
            false,
            dataBindingComponent
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        medicalSubventionInquiryViewModel.viewState.observe(this, Observer {
//            if (it != null) handleViewState(it)
//        })
//        medicalSubventionInquiryViewModel.errorState.observe(this, Observer { error ->
//            showConnectionAlert(requireContext(), retryCallback = {
//                llSuperProgressbar.visibility = View.VISIBLE
//                medicalSubventionInquiryViewModel.getAllContent1(sharedPref.mobile, sharedPref.user)
//            }, cancelCallback = {
//                dialog?.dismiss()
//            }, message = error?.message)
//        })
//        medicalSubventionInquiryViewModel.getAllContent1(sharedPref.mobile, sharedPref.user)
        initializeViews()
    }

    private fun initializeViews() {
//        initializeSpinners()
        renderProvidersTypes()
        binding.bPay.setOnClickListener{
//            navController().navigate(RefundFragmentDirections.openRefundRequest(RefundRequest(sharedPref.name, sharedPref.mobile, sharedPref.user, binding.edCardNumber.text.toString(), "", (binding.spProvider.selectedItem as ProviderUI).branchProfileId.toString(), (binding.spProvider.selectedItem as ProviderUI).providerId.toString(), "9", sharedPref.token, listOf())))
        }
    }

    private fun handleViewState(state: MedicalBuyServiceViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE

//        state.providers?.let {
//            if (it.isEmpty()) {
//                providersResultList?.clear()
//                providersResultList!!.add(ProviderUI(0, getString(R.string.no_data_found), "", "", "", "", "", "", "", "", "", "", "", "", ""))
//            } else
//                providersResultList = it.toMutableList()
//
//            renderProviders()
//            state.providers = null
//            return
//        }
//        state.providerTypes?.let {
//            providersTypesResultList = it.toMutableList()
//            renderProvidersTypes()
//            state.providerTypes = null
//            return
//        }

//        if (state.liteFollowersListUI != null && state.governs != null && state.areas != null) {
//            binding.llHolder.visibility = View.VISIBLE
//            liteFollowersListUI = state.liteFollowersListUI
//            governsResultList = state.governs
//            areasResultList = state.areas
//            initializeViews()
//
//            state.liteFollowersListUI = null
//            state.governs = null
//            state.areas = null
//        }
    }

//    fun loadProviderTypes() {
//        medicalSubventionInquiryViewModel.getProviderTypes(governID.toString(), areaID.toString())
//    }
//
//    fun getProviders(providerTypeID: Int) {
//        medicalSubventionInquiryViewModel.getProvidersByType(providerTypeID.toString(), governID.toString(), areaID.toString(), "")
//    }

    //region

    fun initializeSpinners() {
//        renderFollowers()
//        renderGoverns()
    }
//    fun renderGoverns() {
//        binding.spGovern.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, governsResultList!!)
//        binding.spGovern.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onNothingSelected(parent: AdapterView<*>?) {}
//            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
//                governID = (parent.getItemAtPosition(position) as GovernUI).id
//                renderAreas()
//            }
//        }
//        binding.spGovern.setSelection(0)
//    }
//
//    fun renderAreas() {
//        var filteredAreasList: List<AreaUI>? = mutableListOf()
//
//        filteredAreasList = areasResultList?.filter {
//            it.govId == governID
//        }
//
//        binding.spArea.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, filteredAreasList!!)
//        binding.spArea.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onNothingSelected(parent: AdapterView<*>?) {}
//            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
//                areaID = (parent.getItemAtPosition(position) as AreaUI).id
//                loadProviderTypes()
//            }
//        }
//        binding.spArea.setSelection(0)
//    }
//
//    fun renderFollowers() {
//        binding.spFollower.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, liteFollowersListUI!!)
//        binding.spFollower.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onNothingSelected(parent: AdapterView<*>?) {}
//            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
//                selectedFollower = parent.getItemAtPosition(position) as LiteFollowersListUI
//                binding.edCardNumber.setText(selectedFollower.id.toString())
//            }
//        }
//        binding.spFollower.setSelection(0)
//    }


    fun renderProvidersTypes() {
        providersTypesResultList?.add(ProviderTypeUI(id =0,nameAr ="كوبون فئة 10", "","10",""))
        providersTypesResultList?.add(ProviderTypeUI(id =1,nameAr ="كوبون فئة 20", "","20",""))
        providersTypesResultList?.add(ProviderTypeUI(id =2,nameAr ="كوبون فئة 30", "","30",""))
        providersTypesResultList?.add(ProviderTypeUI(id =3,nameAr ="كوبون فئة 100", "","100",""))
        providersTypesResultList?.add(ProviderTypeUI(id =4,nameAr ="ولادة قيصرية", "","2000",""))
        providersTypesResultList?.add(ProviderTypeUI(id =5,nameAr ="إزالة الزائدة", "","1000",""))
        binding.spServiceType.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, providersTypesResultList!!)
        binding.spServiceType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                providerTypeID = (parent.getItemAtPosition(position) as ProviderTypeUI).id
                binding.tvPrice.text = getString(R.string.total) + ": " + (parent.getItemAtPosition(position) as ProviderTypeUI).createdAt
            }
        }
        binding.spServiceType.setSelection(0)
    }

//    fun renderProviders() {
//        binding.spProvider.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, providersResultList!!)
//        binding.spProvider.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onNothingSelected(parent: AdapterView<*>?) {}
//            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//            }
//        }
//    }
// endregion

    fun navController() = findNavController()
}
