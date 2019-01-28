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
import com.neqabty.databinding.Claiming1FragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.entities.AreaUI
import com.neqabty.presentation.entities.DegreeUI
import com.neqabty.presentation.entities.DoctorUI
import com.neqabty.presentation.entities.SpecializationUI
import com.neqabty.presentation.util.autoCleared
import com.neqabty.testing.OpenForTesting
import kotlinx.android.synthetic.main.claiming1_fragment.*
import javax.inject.Inject

@OpenForTesting
class ClaimingStep1Fragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<Claiming1FragmentBinding>()

    lateinit var claimingViewModel: ClaimingViewModel

    var doctorsResultList: List<DoctorUI>? = mutableListOf()
    var areasResultList: List<AreaUI>? = mutableListOf()
    var degreesResultList: List<DegreeUI>? = mutableListOf()
    var specializationsResultList: List<SpecializationUI>? = mutableListOf()
    var specializationID: String = "1"
    var degreeID: String = "6"
    var areaID: String = "1"

    lateinit var pager: ViewPager
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.claiming1_fragment,
                container,
                false,
                dataBindingComponent
        )
        pager = container as ViewPager
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        claimingViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(ClaimingViewModel::class.java)

        claimingViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        claimingViewModel.errorState.observe(this, Observer { _ ->
            showConnectionAlert(requireContext(),retryCallback =  {
                binding.progressbar.visibility = View.VISIBLE
                claimingViewModel.getAllContent1()
            }, cancelCallback = {
                navController().popBackStack()
                navController().navigate(R.id.homeFragment)
            })
        })
        initializeViews()
        claimingViewModel.getAllContent1()
    }

    private fun handleViewState(state: ClaimingViewState) {
        binding.progressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        binding.svContent.visibility = if (state.isLoading) View.GONE else View.VISIBLE
        state.doctors?.let {
            doctorsResultList = it
        }
        state.areas?.let {
            areasResultList = it
        }
        state.degrees?.let {
            degreesResultList = it
        }
        state.specializations?.let {
            specializationsResultList = it
        }
        if (state.doctors != null && state.specializations != null && state.areas != null && state.degrees != null)
            initializeSpinners()
    }

    fun initializeViews() {
        binding.bNext.setOnClickListener {
            if (isDataValid(binding.edNumber.text.toString() , binding.edCardNumber.text.toString(), spDoctorName.selectedItem)) {
                ClaimingData.number = binding.edNumber.text.toString()
                ClaimingData.doctorName = (spDoctorName.selectedItem as DoctorUI).toString()
                ClaimingData.areaId = (spArea.selectedItem as AreaUI).id
                ClaimingData.doctorId = (spDoctorName.selectedItem as DoctorUI).id
                ClaimingData.professionId = (spSpecialization.selectedItem as SpecializationUI).id
                ClaimingData.degreeId = (spDegree.selectedItem as DegreeUI).id
                pager.setCurrentItem(1, true)
            }
        }
    }

    fun initializeSpinners() {
        renderAreas()
        renderSpecializations()
        renderDegrees()
    }

    //region
    fun renderDoctors() {
        var filteredDoctorsList: List<DoctorUI>? = mutableListOf()

        filteredDoctorsList = doctorsResultList?.filter {
            it.profissionCode.equals(specializationID) && it.areaCode.equals(areaID) && it.degreeCode.equals(degreeID)
        }

        binding.spDoctorName.adapter = ArrayAdapter<DoctorUI>(requireContext(), R.layout.spinner_item, filteredDoctorsList)
        binding.spDoctorName.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
//                val item = parent.getItemAtPosition(position) as DoctorUI
//                        Toast.makeText(requireContext(), item.phoneNumber, LENGTH_LONG).show()

            }
        }
    }

    fun renderAreas() {
        binding.spArea.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, areasResultList)
        binding.spArea.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                areaID = (parent.getItemAtPosition(position) as AreaUI).id.toString()!!
                renderDoctors()
            }
        }
    }

    fun renderDegrees() {
        binding.spDegree.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, degreesResultList)
        binding.spDegree.setSelection(5)
        binding.spDegree.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                degreeID = (parent.getItemAtPosition(position) as DegreeUI).code!!
                renderDoctors()
            }
        }
    }

    fun renderSpecializations() {
        binding.spSpecialization.adapter = ArrayAdapter<SpecializationUI>(requireContext(), R.layout.spinner_item, specializationsResultList)
        binding.spSpecialization.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                specializationID = (parent.getItemAtPosition(position) as SpecializationUI).code!!
                renderDoctors()
            }
        }
    }

    private fun isDataValid(memberNumber: String, cardNumber: String, doctor: Any?): Boolean {
        if (memberNumber.trim().isNotEmpty() && cardNumber.trim().isNotEmpty()
                && doctor != null)
            return true
        else {
            showInvalidDataAlert()
            return false
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
