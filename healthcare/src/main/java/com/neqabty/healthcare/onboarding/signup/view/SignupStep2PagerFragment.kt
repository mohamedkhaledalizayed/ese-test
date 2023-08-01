package com.neqabty.healthcare.onboarding.signup.view

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.neqabty.healthcare.databinding.FragmentSignupStepTwoBinding


class SignupStep2PagerFragment : Fragment() {

    lateinit var binding: FragmentSignupStepTwoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignupStepTwoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initializeViews()
    }

    private fun initializeViews() {
        val timer = object: CountDownTimer(90000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.resendText.text = "إمكانية طلب كود التحقق مرة اخرى بعد : ${ millisUntilFinished / 1000} ثانية."
            }

            override fun onFinish() {
                binding.btnResend.isEnabled = true
            }
        }
        timer.start()

        binding.btnResend.setOnClickListener { (requireActivity() as SignupActivity).sendOTP() }
    }
}
