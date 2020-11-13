package com.neqabty.presentation.ui.medicalRenew

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.MedicalRenewFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.common.Constants
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.entities.MedicalRenewalUI
import com.neqabty.presentation.util.PreferencesHelper
import com.neqabty.presentation.util.autoCleared
import kotlinx.android.synthetic.main.medical_renew_fragment.*
import javax.inject.Inject

class MedicalRenewFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)
    var binding by autoCleared<MedicalRenewFragmentBinding>()
    private var adapter by autoCleared<FollowersAdapter>()

    @Inject
    lateinit var medicalRenewViewModel: MedicalRenewViewModel

    @Inject
    lateinit var appExecutors: AppExecutors

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.medical_renew_fragment,
                container,
                false,
                dataBindingComponent
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        medicalRenewViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(MedicalRenewViewModel::class.java)

        medicalRenewViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        medicalRenewViewModel.errorState.observe(this, Observer { error ->
            showConnectionAlert(requireContext(), retryCallback = {
                llSuperProgressbar.visibility = View.VISIBLE
                medicalRenewViewModel.getMedicalRenewalData(PreferencesHelper(requireContext()).user)
            }, cancelCallback = {
                navController().popBackStack()
                navController().navigate(R.id.homeFragment)
            }, message = error?.message)
        })

        medicalRenewViewModel.getMedicalRenewalData(PreferencesHelper(requireContext()).user)
//        medicalRenewViewModel.getMedicalRenewalData("2305693")
    }

    private fun initializeViews(medicalRenewalUI: MedicalRenewalUI) {
        binding.medicalRenewalData = medicalRenewalUI
        llContent.visibility = View.VISIBLE

        val adapter = FollowersAdapter(dataBindingComponent, appExecutors) { }
        this.adapter = adapter
        binding.rvFollowers.adapter = adapter
        medicalRenewalUI.followers?.let {
            adapter.submitList(it)
        }
        rb_home.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                edAddress.visibility = View.VISIBLE
                tvSyndicate.visibility = View.GONE
            }
        }
        rb_syndicate.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                tvSyndicate.visibility = View.VISIBLE
                edAddress.visibility = View.GONE
            }
        }

        bEdit.setOnClickListener {
            navController().navigate(MedicalRenewFragmentDirections.openMedicalRenewUpdateFragment())
        }
        bContinue.setOnClickListener {
            if (!isDataValid(edMobile.text.toString()))
                return@setOnClickListener
            if ((rb_home.isChecked && edAddress.text.toString().isBlank()) || (rb_syndicate.isChecked && tvDeliveryMethod.text.isNullOrBlank()))
                showAlert(getString(R.string.invalid_data))
            else
                navController().navigate(MedicalRenewFragmentDirections.openMedicalRenewDetailsFragment(if (rb_syndicate.isChecked) Constants.DELIVERY_LOCATION_SYNDICATE else Constants.DELIVERY_LOCATION_HOME, edAddress.text.toString(), edMobile.text.toString(), medicalRenewalUI))
        }
    }

    private fun handleViewState(state: MedicalRenewViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        state.medicalRenewalUI?.let {
            state.medicalRenewalUI?.contact?.contactID = PreferencesHelper(requireContext()).user
            initializeViews(state.medicalRenewalUI!!)
        }
    }

    //region

    private fun isDataValid(mobile: String): Boolean {
        return if (mobile.matches(Regex("[0-9]*")) && mobile.trim().length == 11 && (mobile.substring(0, 3).equals("012") || mobile.substring(0, 3).equals("010") || mobile.substring(0, 3).equals("011") || mobile.substring(0, 3).equals("015")))
            true
        else {
            showAlert(getString(R.string.invalid_mobile))
            false
        }
    }
// endregion

    fun navController() = findNavController()
}
