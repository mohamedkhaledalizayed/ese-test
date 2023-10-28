package com.neqabty.healthcare.core.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.data.Constants
import com.neqabty.healthcare.core.data.PreferencesHelper
import com.neqabty.healthcare.databinding.FragmentChangeAccountDialogBinding
import com.neqabty.healthcare.onboarding.signup.view.SignupActivity
import com.neqabty.healthcare.splash.view.SplashActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ChangeAccountDialog : DialogFragment() {

    @Inject
    lateinit var sharedPreferences: PreferencesHelper
    private lateinit var binding: FragmentChangeAccountDialogBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChangeAccountDialogBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.agree.setOnClickListener {
            dismiss()
            Constants.isSyndicateMember = false
            Constants.selectedSyndicateCode = ""
            Constants.selectedSyndicatePosition = 0
            sharedPreferences.clearAll()
            startActivity(Intent(requireContext(), SplashActivity::class.java))
            requireActivity().finishAffinity()
        }

        binding.disagree.setOnClickListener {
            dismiss()
        }

    }

}