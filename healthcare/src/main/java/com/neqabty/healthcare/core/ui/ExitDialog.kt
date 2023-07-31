package com.neqabty.healthcare.core.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.neqabty.healthcare.databinding.FragmentExitDialogBinding


class ExitDialog : DialogFragment() {

    private lateinit var binding: FragmentExitDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExitDialogBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.agree.setOnClickListener {
            dismiss()
            requireActivity().finishAffinity()
        }

        binding.disagree.setOnClickListener {
            dismiss()
        }

    }
}