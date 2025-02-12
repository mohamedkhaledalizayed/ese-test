package com.neqabty.presentation.ui.medicalProfessions

import androidx.lifecycle.Observer
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.MedicalProfessionsFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MedicalProfessionsFragment : BaseFragment() {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<MedicalProfessionsFragmentBinding>()
    private var adapter by autoCleared<MedicalProfessionsAdapter>()
    private val medicalProfessionsViewModel: MedicalProfessionsViewModel by viewModels()

    @Inject
    lateinit var appExecutors: AppExecutors

    var areaID: Int = 0
    var governID: Int = 0
    var categoryId: Int = 0
    var title: String = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.medical_professions_fragment,
                container,
                false,
                dataBindingComponent
        )

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val params = MedicalProfessionsFragmentArgs.fromBundle(arguments!!)
//        title = params.title
        categoryId = params.categoryId
        areaID = params.areaID
        governID = params.governID
        if (title.isNotBlank()) { setToolbarTitle(title) }

        val adapter = MedicalProfessionsAdapter(dataBindingComponent, appExecutors) { profession ->
            navController().navigate(
                    MedicalProfessionsFragmentDirections.openProviders("", categoryId, governID, areaID, "",profession.id.toString(), "")
            )
        }
        this.adapter = adapter
        binding.rvProfessions.adapter = adapter

        medicalProfessionsViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        medicalProfessionsViewModel.errorState.observe(this, Observer { error ->
            showConnectionAlert(requireContext(), retryCallback = {
                llSuperProgressbar.visibility = View.VISIBLE
                medicalProfessionsViewModel.getMedicalProfessions(categoryId.toString(), governID.toString(), areaID.toString())
            }, cancelCallback = {
                navController().navigateUp()
            }, message = error?.message)
        })

        medicalProfessionsViewModel.getMedicalProfessions(categoryId.toString(), governID.toString(), areaID.toString())
    }

    private fun handleViewState(state: MedicalProfessionsViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        state.professions?.let {
            adapter.submitList(it)
        }
    }
    fun initializeViews() {
    }

//region

// endregion

    fun navController() = findNavController()
}
