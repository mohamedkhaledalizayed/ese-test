package com.neqabty.presentation.ui.updateDataVerification

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.UpdateDataVerificationFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.util.PreferencesHelper
import com.neqabty.presentation.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.update_data_verification_fragment.*
import javax.inject.Inject

@AndroidEntryPoint
class UpdateDataVerificationFragment : BaseFragment() {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<UpdateDataVerificationFragmentBinding>()

    @Inject
    lateinit var appExecutors: AppExecutors
    var verificationCode: String = ""
    private var counterTimeout: Long = 30000
    private var isTimerFinished = true
    lateinit var timer: CountDownTimer
    private val updateDataVerificationViewModel: UpdateDataVerificationViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.update_data_verification_fragment,
                container,
                false,
                dataBindingComponent
        )

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        updateDataVerificationViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        updateDataVerificationViewModel.errorState.observe(this, Observer { error ->
            showConnectionAlert(requireContext(), retryCallback = {
                llSuperProgressbar.visibility = View.VISIBLE
                verifyUser()
            }, cancelCallback = {
                navController().navigateUp()
            }, message = error?.message)
        })
        initializeViews()
    }

    override fun onPause() {
        super.onPause()
//        binding.counter = ""
//        cancelTimer()
//        tvResend.isEnabled = true
    }

    private fun verifyUser() {
        updateDataVerificationViewModel.verifyUser(sharedPref.user, sharedPref.mobile)
    }

    private fun handleViewState(state: UpdateDataVerificationViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        if (!state.isLoading && state.code.isNotEmpty()) {
            verificationCode = state.code
            startCountingDown()
        }
    }

    fun initializeViews() {
        bSend.setOnClickListener {
            if (binding.edVerificationNumber.text.toString() == verificationCode && binding.edVerificationNumber.text.toString().isNotEmpty())
                navController().navigate(UpdateDataVerificationFragmentDirections.updateDataDetails())
            else
                showErrorAlert(getString(R.string.wrong_verification_code))
        }
        tvResend.setOnClickListener {
            verifyUser()
        }
        startCountingDown()
    }


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

    fun showErrorAlert(message: String) {
        builder = AlertDialog.Builder(requireContext())
        builder?.setTitle(getString(R.string.thanks))
        builder?.setMessage(message)
        builder?.setCancelable(true)
        builder?.setPositiveButton(getString(R.string.ok_btn)) { dialog, which ->
            dialog.dismiss()
        }
        var dialog = builder?.create()
        dialog?.show()
    }
// endregion

    fun navController() = findNavController()
}
