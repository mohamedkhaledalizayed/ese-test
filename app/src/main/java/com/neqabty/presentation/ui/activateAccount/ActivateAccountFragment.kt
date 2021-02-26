package com.neqabty.presentation.ui.activateAccount

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import com.neqabty.R
import com.neqabty.databinding.ActivateAccountFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.common.Constants
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.ui.trips.TripsData
import com.neqabty.presentation.util.PreferencesHelper
import com.neqabty.presentation.util.autoCleared
import kotlinx.android.synthetic.main.activate_account_fragment.*
import javax.inject.Inject

class ActivateAccountFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<ActivateAccountFragmentBinding>()

    lateinit var activateAccountViewModel: ActivateAccountViewModel
    var type: Int? = 0
    var password: String = ""

    var verificationCode: String = ""
    private var counterTimeout: Long = 30000
    private var isTimerFinished = true
    lateinit var timer: CountDownTimer
    var otp = ""
    lateinit var receiver: BroadcastReceiver
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
        otp = params.otp
        password = params.password

        activateAccountViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(ActivateAccountViewModel::class.java)

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
//        activateAccountViewModel.sendSMS(PreferencesHelper(requireContext()).mobile)
        receiver = object : BroadcastReceiver() {
            override fun onReceive(contxt: Context?, intent: Intent?) {
                if (intent?.action.equals("otp",true)) {
                    val message = intent?.getStringExtra("message") ?: ""
                    otp = message.substring(0, 4)
                    password = message.substring(4, message.length)
                    edOTP.setText(otp)
                    edPassword.setText(password)
                }
            }
        }
        initializeViews()
    }

    fun initializeViews() {
        edOTP.setText(otp)
        edPassword.setText(password)
        binding.bSend.setOnClickListener {
            if (binding.edOTP.text.toString().isNotEmpty() && binding.edOTP.text.toString().length == 4)
                activateAccount()
            else
                showAlert(getString(R.string.wrong_verification_code))
        }

        tvResend.setOnClickListener {
            activateAccountViewModel.sendSMS(PreferencesHelper(requireContext()).mobile)
        }
        startCountingDown()
    }

    private fun handleViewState(state: ActivateAccountViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        if (state.isSuccessful && state.user == null) {// got code
            startCountingDown()
        } else if (state.isSuccessful && state.user != null) {
            activity?.invalidateOptionsMenu()
           PreferencesHelper(requireContext()).user = state.user?.details!![0].userNumber!!
           PreferencesHelper(requireContext()).name = state.user?.details!![0].name!!
           PreferencesHelper(requireContext()).isRegistered = true
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

                Constants.CORONA -> navController().navigate(
                        ActivateAccountFragmentDirections.openCorona()
                )

                Constants.MEDICAL_RENEW -> navController().navigate(
                        ActivateAccountFragmentDirections.openMedicalRenew()
                )
            }
        }
    }

    fun activateAccount() {
        activateAccountViewModel.activateAccount(PreferencesHelper(requireContext()).mobile, binding.edOTP.text.toString(), edPassword.text.toString())
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

    override fun onResume() {
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(receiver, IntentFilter("otp"))
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(receiver)
    }
// endregion

    fun navController() = findNavController()
}
