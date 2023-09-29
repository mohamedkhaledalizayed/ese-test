package com.neqabty.presentation.ui.medicalArchive

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
import com.neqabty.databinding.MedicalArchiveFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.entities.ArchiveUploadCategoryUI
import com.neqabty.presentation.entities.ArchiveUploadItemUI
import com.neqabty.presentation.entities.ProviderTypeUI
import com.neqabty.presentation.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MedicalArchiveFragment : BaseFragment() {
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)
    var binding by autoCleared<MedicalArchiveFragmentBinding>()

    private val medicalArchiveViewModel: MedicalArchiveViewModel by viewModels()

    var categoriesResultList: MutableList<ArchiveUploadCategoryUI>? = mutableListOf()
    var uploadsResultList: MutableList<ArchiveUploadItemUI>? = mutableListOf()
    var selectedCategoryID: Int = 0

    @Inject
    lateinit var appExecutors: AppExecutors

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.medical_archive_fragment,
            container,
            false,
            dataBindingComponent
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        medicalArchiveViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        medicalArchiveViewModel.errorState.observe(this, Observer { error ->
            showConnectionAlert(requireContext(), retryCallback = {
                llSuperProgressbar.visibility = View.VISIBLE
            }, cancelCallback = {
                dialog?.dismiss()
            }, message = error?.message)
        })
        medicalArchiveViewModel.getCategories()
    }

    private fun initializeViews() {
        renderCategories()
        binding.fabAdd.setOnClickListener{
//            navController().navigate(RefundFragmentDirections.openRefundRequest(RefundRequest(sharedPref.name, sharedPref.mobile, sharedPref.user, binding.edCardNumber.text.toString(), "", (binding.spProvider.selectedItem as ProviderUI).branchProfileId.toString(), (binding.spProvider.selectedItem as ProviderUI).providerId.toString(), "9", sharedPref.token, listOf())))
        }
    }

    private fun handleViewState(state: MedicalArchiveViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        state.archiveUploadItemUIList?.let {

            binding.rvFiles.adapter =  MedicalArchiveAdapter(dataBindingComponent, appExecutors) { item ->
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(item.doc1)))
            }
            (binding.rvFiles.adapter as MedicalArchiveAdapter).submitList(it)
            return
        }

        if (state.archiveUploadCategoryUIList != null) {
            binding.llHolder.visibility = View.VISIBLE
            categoriesResultList = state.archiveUploadCategoryUIList!!.toMutableList()
            state.archiveUploadCategoryUIList = null
            initializeViews()
        }
    }

//    fun loadProviderTypes() {
//        medicalSubventionInquiryViewModel.getProviderTypes(governID.toString(), areaID.toString())
//    }
//
//    fun getProviders(providerTypeID: Int) {
//        medicalSubventionInquiryViewModel.getProvidersByType(providerTypeID.toString(), governID.toString(), areaID.toString(), "")
//    }

    //region
    fun renderCategories() {
        binding.spCategoryType.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, categoriesResultList!!)
        binding.spCategoryType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedCategoryID = (parent.getItemAtPosition(position) as ArchiveUploadCategoryUI).id
                medicalArchiveViewModel.getUploadsList(sharedPref.user, selectedCategoryID)
            }
        }
        binding.spCategoryType.setSelection(0)
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
