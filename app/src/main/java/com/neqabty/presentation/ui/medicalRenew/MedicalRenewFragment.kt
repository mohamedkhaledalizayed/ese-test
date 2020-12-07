package com.neqabty.presentation.ui.medicalRenew

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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

    lateinit var medicalRenewalUI: MedicalRenewalUI
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

//        PreferencesHelper(requireContext()).user = "8608478"
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

    private fun initializeViews() {
        binding.medicalRenewalData = medicalRenewalUI
        llContent.visibility = View.VISIBLE

        edMobile.setText(medicalRenewalUI.contact!!.mobile ?: "")
        edAddress.setText(medicalRenewalUI.contact!!.address ?: "")
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
        rb_mainSyndicate.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                edAddress.visibility = View.GONE
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
            if (isEngineerDataValid())
                navController().navigate(MedicalRenewFragmentDirections.openMedicalRenewUpdateFragment())
        }
        bContinue.setOnClickListener {
            if ((rb_home.isChecked && edAddress.text.toString().isBlank())) {
                showAlert(getString(R.string.invalid_data))
                return@setOnClickListener
            } else if (!isEngineerDataValid())
                return@setOnClickListener
            else if (!isDataValid(edMobile.text.toString()))
                return@setOnClickListener
            else
                navController().navigate(MedicalRenewFragmentDirections.openMedicalRenewDetailsFragment(if (rb_syndicate.isChecked) Constants.DELIVERY_LOCATION_SYNDICATE else if (rb_home.isChecked) Constants.DELIVERY_LOCATION_HOME else Constants.DELIVERY_LOCATION_MAIN_SYNDICATE, edAddress.text.toString(), edMobile.text.toString(), medicalRenewalUI))
        }
//        edMobile.setText("01119850766")
//        medicalRenewalUI.requestStatus = -1 // TODO TODOTODO TODOTODO TODOTODO TODOTODO TODOTODO TODO
        when (medicalRenewalUI.requestStatus) {
            -1 -> tvRequestStatus.visibility = View.GONE
            0 -> {
                bEdit.visibility = View.GONE
                bContinue.visibility = View.GONE
                tvRequestStatus.setTextColor(resources.getColor(R.color.blue))
                tvRequestStatus.text = getString(R.string.medical_update_request_status_processing)
            }
            1 -> {
                tvRequestStatus.setTextColor(resources.getColor(R.color.green))
                tvRequestStatus.text = getString(R.string.medical_update_request_status_approved)
            }
            2 -> {
                tvRequestStatus.setTextColor(resources.getColor(R.color.red))
                if (medicalRenewalUI.rejectionMsg.isNullOrEmpty())
                    tvRequestStatus.text = getString(R.string.medical_update_request_status_rejected)
                else
                    tvRequestStatus.text = getString(R.string.medical_update_request_status_rejected) + "\n" + getString(R.string.medical_update_request_rejection_reason) + " " + medicalRenewalUI.rejectionMsg
            }
        }
    }

    private fun checkStatus() {
//        medicalRenewalUI.engineerStatus = 0
//        medicalRenewalUI.contact?.pic = "jhkdfjv"
//        medicalRenewalUI.contact?.syndicateName = "jhcj,d"
        when (medicalRenewalUI.engineerStatus) {
            -1 -> {
                showInEligibleMemberAlert(getString(R.string.error_msg))
                return
            }
            1 -> {
                showInEligibleMemberAlert(getString(R.string.medical_subscription_unsubscribed))
                return
            }
            2 -> {
                showInEligibleMemberAlert(getString(R.string.medical_subscription_suspended))
                return
            }
            3 -> {
                showShouldRenewSyndicateMembershipAlert(getString(R.string.medical_subscription_renew_syndicate_membership))
                bContinue.visibility = View.GONE
            }
        }
        if (medicalRenewalUI.contact?.isDead == true && medicalRenewalUI.followers?.size == 0) {
            bContinue.visibility = View.GONE
        }
        when (medicalRenewalUI.healthCareStatus) {
            -1 -> {
                showInEligibleMemberAlert(getString(R.string.error_msg))
                return
            }
            1 -> {
                showNewMemberAlert()
            }
            3 -> bContinue.visibility = View.GONE
            4 -> bContinue.visibility = View.GONE
            5 -> bEdit.visibility = View.GONE
        }
    }

    private fun handleViewState(state: MedicalRenewViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        state.medicalRenewalUI?.let {
            state.medicalRenewalUI?.oldRefId = PreferencesHelper(requireContext()).user
            medicalRenewalUI = state.medicalRenewalUI!!
            checkStatus()
            initializeViews()
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

    private fun isEngineerDataValid(): Boolean {
        return if (medicalRenewalUI.contact?.pic.isNullOrEmpty() || medicalRenewalUI.contact?.syndicateName.isNullOrEmpty()) {
            showAlert(getString(R.string.medical_subscription_renew_incomplete_data)) {
                navController().popBackStack()
                navController().navigate(R.id.homeFragment)
            }
            false
        } else if (!medicalRenewalUI.contact?.mobile.isNullOrEmpty() && !medicalRenewalUI.contact?.address.isNullOrEmpty() && !medicalRenewalUI.contact?.name.isNullOrEmpty() && !medicalRenewalUI.contact?.nationalId.isNullOrEmpty() && !medicalRenewalUI.contact?.birthDate.isNullOrEmpty())
            true
        else {
            Toast.makeText(requireContext(), getString(R.string.medical_subscription_renew_incomplete_data), Toast.LENGTH_LONG).show()
            true
        }
    }

    private fun showNewMemberAlert() {
        builder = AlertDialog.Builder(requireContext())
        builder?.setTitle(getString(R.string.alert_title))
        builder?.setCancelable(false)
        builder?.setMessage(getString(R.string.medical_subscribe_confirmation))
        builder?.setPositiveButton(getString(R.string.alert_yes)) { dialog, which ->
            initializeViews()
        }
        builder?.setNegativeButton(getString(R.string.alert_no)) { dialog, which ->
            navController().popBackStack()
            navController().navigate(R.id.homeFragment)
        }

        if (dialog == null)
            dialog = builder?.create()

        if (!dialog?.isShowing!!)
            dialog?.show()

    }

    private fun showInEligibleMemberAlert(msg: String) {
        builder = AlertDialog.Builder(requireContext())
        builder?.setTitle(getString(R.string.alert_title))
        builder?.setCancelable(false)
        builder?.setMessage(msg)
        builder?.setNegativeButton(getString(R.string.ok_btn)) { dialog, which ->
            navController().popBackStack()
            navController().navigate(R.id.homeFragment)
        }

        if (dialog == null)
            dialog = builder?.create()

        if (!dialog?.isShowing!!)
            dialog?.show()

    }


    private fun showShouldRenewSyndicateMembershipAlert(msg: String) {
        builder = AlertDialog.Builder(requireContext())
        builder?.setTitle(getString(R.string.alert_title))
        builder?.setCancelable(false)
        builder?.setMessage(msg)
        builder?.setPositiveButton(getString(R.string.ok_btn)) { dialog, which ->
            dialog.dismiss()
        }

        builder?.setNegativeButton(getString(R.string.cancel_btn)) { dialog, which ->
            navController().popBackStack()
            navController().navigate(R.id.homeFragment)
        }

        if (dialog == null)
            dialog = builder?.create()

        if (!dialog?.isShowing!!)
            dialog?.show()

    }

    private fun showGoToRenewalPaymentAlert(msg: String) {
        builder = AlertDialog.Builder(requireContext())
        builder?.setTitle(getString(R.string.alert_title))
        builder?.setCancelable(false)
        builder?.setMessage(msg)
        builder?.setPositiveButton(getString(R.string.go_to_payment)) { dialog, which ->
            navController().popBackStack()
            navController().navigate(R.id.inquiryFragment)
        }

        builder?.setNegativeButton(getString(R.string.cancel_btn)) { dialog, which ->
            navController().popBackStack()
            navController().navigate(R.id.homeFragment)
        }

        if (dialog == null)
            dialog = builder?.create()

        if (!dialog?.isShowing!!)
            dialog?.show()

    }
// endregion

    fun navController() = findNavController()
}
