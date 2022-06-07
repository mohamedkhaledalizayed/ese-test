package com.neqabty.presentation.ui.activateAccount

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.neqabty.R
import com.neqabty.databinding.ActivateAccountFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.common.Constants
import com.neqabty.presentation.ui.trips.TripsData
import com.neqabty.presentation.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activate_account_fragment.*

@AndroidEntryPoint
class ActivateAccountFragment : BaseFragment() {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<ActivateAccountFragmentBinding>()

    private val activateAccountViewModel: ActivateAccountViewModel by viewModels()
    var type: Int? = 0
    var password: String = ""

    var verificationCode: String = ""
    private var counterTimeout: Long = 120000
    private var isTimerFinished = true
    lateinit var timer: CountDownTimer
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.activate_account_fragment,
                container,
                false,
                dataBindingComponent
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val params = ActivateAccountFragmentArgs.fromBundle(arguments!!)
        type = params.type

        activateAccountViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        activateAccountViewModel.errorState.observe(this, Observer { error ->
            showConnectionAlert(requireContext(), retryCallback = {
                activateAccount()
            }, cancelCallback = {
                llSuperProgressbar.visibility = View.GONE
            }, message = error?.message)
        })
//        activateAccountViewModel.sendSMS(sharedPref.mobile)
        initializeViews()
    }

    fun initializeViews() {
//        edPassword.setText(password)
        binding.edMobile.setText(sharedPref.mobile)
        binding.bSend.setOnClickListener {
            if (binding.edOTP.text.toString().isNotEmpty() && binding.edOTP.text.toString().length == 4)
                activateAccount()
            else
                showAlert(getString(R.string.wrong_verification_code))
        }

        tvResend.setOnClickListener {
            activateAccountViewModel.sendSMS(sharedPref.mobile)
        }
        startCountingDown()
    }

    private fun handleViewState(state: ActivateAccountViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        if (state.isSuccessful && state.user == null) {// got code
            startCountingDown()
        } else if (state.isSuccessful && state.user != null) {
            activity?.invalidateOptionsMenu()
           sharedPref.user = state.user?.details!![0].userNumber!!
           sharedPref.name = state.user?.details!![0].name!!
           sharedPref.isRegistered = true
//            showTwoButtonsAlert(message = getString(R.string.welcome_with_name, state.user?.details!![0].name!!),
//                    okCallback = {
//                        state.user = null
//                        navController().navigateUp()
//                    }, cancelCallback = { state.user = null })
            when (type) {
                Constants.CLAIMING -> navController().navigate(
                        ActivateAccountFragmentDirections.openClaiming()
                )

                Constants.TRIPS -> navController().navigate(
                        ActivateAccountFragmentDirections.openTripReservation(TripsData.tripItem!!)
                )

                Constants.RECORDS -> navController().navigate(
                        ActivateAccountFragmentDirections.openEngineeringRecords()
                )

                Constants.UPDATE_DATA -> navController().navigate(
                        ActivateAccountFragmentDirections.openUpdateDataVerification()
                )

                Constants.COMPLAINTS -> navController().navigate(
                        ActivateAccountFragmentDirections.openComplaints()
                )

                Constants.QUESTIONNAIRE -> navController().navigate(
                        ActivateAccountFragmentDirections.openQuestionnaire()
                )

                Constants.MEDICAL_RENEW -> navController().navigate(
                        ActivateAccountFragmentDirections.openMedicalRenew()
                )

                Constants.ONLINE_PHARMACY -> navController().navigate(
                        ActivateAccountFragmentDirections.openOnlinePharmacy()
                )

                Constants.DOCTORS_RESERVATION -> navController().navigate(
                    ActivateAccountFragmentDirections.openDoctorsReservation()
                )

                Constants.MEDICAL_LETTERS -> navController().navigate(
                        ActivateAccountFragmentDirections.openMedicalLetters()
                )

                Constants.MEDICAL_LETTERS_INQUIRY -> navController().navigate(
                        ActivateAccountFragmentDirections.openMedicalLettersInquiry()
                )

                Constants.TRACK_SHIPMENT -> navController().navigate(
                        ActivateAccountFragmentDirections.openTrackShipment()
                )

                Constants.CHANGE_USER_MOBILE -> navController().navigate(
                        ActivateAccountFragmentDirections.openChangeUserMobile()
                )

                Constants.CHANGE_PASSWORD -> navController().navigate(
                        ActivateAccountFragmentDirections.openChangePassword(false, sharedPref.mobile)
                )

                Constants.COMMITTEES -> navController().navigate(
                    ActivateAccountFragmentDirections.openCommittees()
                )

                Constants.MEDICAL_PROCEDURES_INQUIRY -> navController().navigate(
                    ActivateAccountFragmentDirections.openMedicalProceduresInquiry()
                )

            }
        }
    }

    fun activateAccount() {
        activateAccountViewModel.activateAccount(sharedPref.mobile, binding.edOTP.text.toString(), edPassword.text.toString())
    }

    //region
    fun startCountingDown() {
        if (isTimerFinished) {
            timer = object : CountDownTimer(counterTimeout, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    try {
                        binding.counter = (millisUntilFinished / 1000).toString()
                    } catch (e: Exception) {
                    }
                }

                override fun onFinish() {
                    try {
                        binding.counter = ""
                        tvResend.setTextColor(resources.getColor(R.color.colorPrimary))
                        tvResend.isEnabled = true
                    } catch (e: Exception) {
                    }
                    timer.cancel()
                    isTimerFinished = true
                }
            }
            timer.start()
            isTimerFinished = false

            tvResend.isEnabled = false
            tvResend.setTextColor(resources.getColor(R.color.gray_dark))
        }
    }
// endregion

    fun navController() = findNavController()
}
