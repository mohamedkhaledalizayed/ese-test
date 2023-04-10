package com.neqabty.healthcare.commen.onboarding.signup.view

import `in`.aabhasjindal.otptextview.OTPListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
        binding.tvResend.setOnClickListener { (requireActivity() as SignupActivity).sendOTP() }
    }
}
