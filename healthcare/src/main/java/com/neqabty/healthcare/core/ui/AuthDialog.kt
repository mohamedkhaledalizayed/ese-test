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
import com.neqabty.healthcare.databinding.FragmentAuthDialogBinding
import com.neqabty.healthcare.onboarding.signup.view.SignupActivity


class AuthDialog : DialogFragment() {


    private lateinit var binding: FragmentAuthDialogBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =  FragmentAuthDialogBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.agree.setOnClickListener {
            dismiss()
            Constants.isSyndicateMember = false
            Constants.selectedSyndicateCode = ""
            Constants.selectedSyndicatePosition = 0
            startActivity(Intent(requireContext(), SignupActivity::class.java))
            requireActivity().finishAffinity()
        }

        binding.disagree.setOnClickListener {
            dismiss()
        }
    }

}