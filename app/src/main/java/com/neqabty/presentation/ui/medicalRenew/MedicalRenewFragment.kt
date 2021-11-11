package com.neqabty.presentation.ui.medicalRenew

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.MedicalRenewFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.common.Constants
import com.neqabty.presentation.entities.MedicalRenewalPaymentUI
import com.neqabty.presentation.entities.MedicalRenewalUI
import com.neqabty.presentation.ui.ads.AdsActivity
import com.neqabty.presentation.ui.common.PdfCreatorScreen
import com.neqabty.presentation.ui.medicalRenewDetails.MedicalRenewPaymentItemsAdapter
import com.neqabty.presentation.util.PreferencesHelper
import com.neqabty.presentation.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.medical_renew_fragment.*
import javax.inject.Inject

@AndroidEntryPoint
class MedicalRenewFragment : BaseFragment() {
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)
    var binding by autoCleared<MedicalRenewFragmentBinding>()
    private var followersAdapter by autoCleared<FollowersAdapter>()
    private var medicalRenewPaymentItemsAdapter by autoCleared<MedicalRenewPaymentItemsAdapter>()

    private val medicalRenewViewModel: MedicalRenewViewModel by viewModels()

    @Inject
    lateinit var appExecutors: AppExecutors
    lateinit var medicalRenewalPaymentUI: MedicalRenewalPaymentUI
    lateinit var medicalRenewalUI: MedicalRenewalUI

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(Constants.healthCareProjectStatusMsg.isNotBlank()) Toast.makeText(requireContext(), Constants.healthCareProjectStatusMsg, Toast.LENGTH_LONG).show()

        showAds(Constants.AD_MEDICAL_RENEW)
    }
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

//        PreferencesHelper(requireContext()).user = "2502813"

        medicalRenewViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        medicalRenewViewModel.errorState.observe(this, Observer { error ->
            showConnectionAlert(requireContext(), retryCallback = {
                llSuperProgressbar.visibility = View.VISIBLE
                medicalRenewViewModel.getMedicalRenewalData(PreferencesHelper(requireContext()).mobile, PreferencesHelper(requireContext()).user)
                medicalRenewViewModel.paymentInquiry(PreferencesHelper(requireContext()).mobile, PreferencesHelper(requireContext()).user, 1, "address", "mobile")
            }, cancelCallback = {
                navController().popBackStack()
                navController().navigate(R.id.homeFragment)
            }, message = error?.message)
        })

        medicalRenewViewModel.getMedicalRenewalData(PreferencesHelper(requireContext()).mobile, PreferencesHelper(requireContext()).user)
        medicalRenewViewModel.paymentInquiry(PreferencesHelper(requireContext()).mobile, PreferencesHelper(requireContext()).user, 1, "address", "mobile")

    }

    private fun initializeViews() {
        binding.medicalRenewalData = medicalRenewalUI
        llContent.visibility = View.VISIBLE

        val followersAdapter = FollowersAdapter(dataBindingComponent, appExecutors) { }
        this.followersAdapter = followersAdapter
        binding.rvFollowers.adapter = followersAdapter
        medicalRenewalUI.followers?.let {
            followersAdapter.submitList(it)
        }

        val medicalRenewPaymentItemsAdapter = MedicalRenewPaymentItemsAdapter(dataBindingComponent, appExecutors) { }
        this.medicalRenewPaymentItemsAdapter = medicalRenewPaymentItemsAdapter
        medicalRenewalPaymentUI?.let {
            binding.medicalRenewalPayment = it
        }

        binding.rvDetails.adapter = medicalRenewPaymentItemsAdapter
        medicalRenewalPaymentUI.paymentItem?.paymentDetailsItems?.let {
            medicalRenewPaymentItemsAdapter.submitList(it)
        }

        rb_home.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                edAddress.visibility = View.VISIBLE
                edMobile.visibility = View.VISIBLE
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

        bUpload.setOnClickListener {
            var pdfIntent = Intent(context, PdfCreatorScreen::class.java)
//            medicalRenewalUI.followers!!.add(medicalRenewalUI.followers!![0])
//            medicalRenewalUI.followers!!.add(medicalRenewalUI.followers!![0])
//            medicalRenewalUI.followers!!.add(medicalRenewalUI.followers!![0])
//            medicalRenewalUI.followers!!.add(medicalRenewalUI.followers!![0])
//            medicalRenewalUI.followers!!.add(medicalRenewalUI.followers!![0])
//            medicalRenewalUI.followers!!.add(medicalRenewalUI.followers!![0])
//            medicalRenewalUI.followers!!.add(medicalRenewalUI.followers!![0])
//            medicalRenewalUI.followers!!.add(medicalRenewalUI.followers!![0])

            val temp = medicalRenewalUI.deepClone(medicalRenewalUI)
            temp?.contact?.pic = ""
            temp?.followers = temp?.followers?.filter { it.lastMedYear != null && it.lastMedYear!!.toInt() >= 2021 }?.toMutableList()
            for (item: MedicalRenewalUI.FollowerItem in temp?.followers!!) {
                item.pic = ""
                item.attachments = mutableListOf()
            }
            pdfIntent.putExtra("data", temp)
            this.startActivity(pdfIntent)
        }
//        edMobile.setText("01119850766")
//        medicalRenewalUI.requestStatus = -1 // TODO TODOTODO TODOTODO TODOTODO TODOTODO TODOTODO TODO
        when (medicalRenewalUI.requestStatus) {
            -1 -> tvRequestStatus.visibility = View.GONE
            0 -> {
                bEdit.visibility = View.GONE
                bContinue.visibility = View.GONE
                llDelivery.visibility = View.GONE
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
        if (!Constants.isHealthCareProjectEnabled) {
            bContinue.visibility = View.GONE
            llDelivery.visibility = View.GONE
        }
    }

    private fun checkStatus() {
//        medicalRenewalUI.healthCareStatus = 0
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
                showGoToRenewalPaymentAlert(getString(R.string.medical_subscription_renew_syndicate_membership))
                bEdit.visibility = View.GONE
                bContinue.visibility = View.GONE
                llDelivery.visibility = View.GONE
            }
        }
//        if (medicalRenewalUI.contact?.isDead == true && medicalRenewalUI.followers?.size == 0) {
//            bContinue.visibility = View.GONE
//            llDelivery.visibility = View.GONE
//        }
        when (medicalRenewalUI.healthCareStatus) {
            -1 -> {
                showInEligibleMemberAlert(getString(R.string.error_msg))
                return
            }
            1 -> {
                showNewMemberAlert()
            }
            3 -> {
                tvSubscribtionStatus.text = getString(R.string.medical_subscription_subscribed)
                tvSubscribtionStatus.visibility = View.VISIBLE
                bUpload.visibility = View.VISIBLE
                bContinue.visibility = if (medicalRenewalPaymentUI.paymentItem?.amount == null || medicalRenewalPaymentUI.paymentItem?.amount == 0) View.GONE else View.VISIBLE
                llDelivery.visibility = if (medicalRenewalPaymentUI.paymentItem?.amount == null || medicalRenewalPaymentUI.paymentItem?.amount == 0) View.GONE else View.VISIBLE
            }
            4 -> {
                bContinue.visibility = View.GONE
                llDelivery.visibility = View.GONE
            }
//            5 -> {
//                bEdit.visibility = View.GONE
//            }
            6 -> {
                tvSubscribtionStatus.setText(getString(R.string.medical_subscription_status_pending))
                tvSubscribtionStatus.visibility = View.VISIBLE
                tvRequestStatus.visibility = View.GONE
                bContinue.visibility = View.GONE
                llDelivery.visibility = View.GONE
                bEdit.visibility = View.GONE
            }
        }
    }

    private fun handleViewState(state: MedicalRenewViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE

        if (state.medicalRenewalUI != null && state.medicalRenewalPayment != null) {
            llSuperProgressbar.visibility = View.GONE

            state.medicalRenewalUI?.oldRefId = PreferencesHelper(requireContext()).user
            medicalRenewalUI = state.medicalRenewalUI!!
            medicalRenewalPaymentUI = state.medicalRenewalPayment as MedicalRenewalPaymentUI
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
        return if (medicalRenewalUI.contact?.syndicateName.isNullOrEmpty()) {//medicalRenewalUI.contact?.pic.isNullOrEmpty() ||
            showAlert(getString(R.string.medical_subscription_renew_incomplete_data)) {
                navController().popBackStack()
                navController().navigate(R.id.homeFragment)
            }
            false
        } else if (!medicalRenewalUI.contact?.mobile.isNullOrEmpty() && !medicalRenewalUI.contact?.address.isNullOrEmpty() && !medicalRenewalUI.contact?.name.isNullOrEmpty() && !medicalRenewalUI.contact?.nationalId.isNullOrEmpty() && !medicalRenewalUI.contact?.birthDate.isNullOrEmpty())
            true
        else {
            Toast.makeText(requireContext(), getString(R.string.medical_subscription_renew_incomplete_data), Toast.LENGTH_SHORT).show()
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


        var dialog = builder?.create()
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


        var dialog = builder?.create()
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

        builder?.setNegativeButton(getString(R.string.continue_btn)) { dialog, which ->
            dialog.dismiss()
        }


        var dialog = builder?.create()
        dialog?.show()

    }
// endregion

    fun navController() = findNavController()
}
