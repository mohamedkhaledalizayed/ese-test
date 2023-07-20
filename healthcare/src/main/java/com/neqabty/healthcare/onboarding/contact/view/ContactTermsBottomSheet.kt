package com.neqabty.healthcare.onboarding.contact.view


import android.annotation.SuppressLint
import android.app.Dialog
import android.view.ViewGroup
import android.widget.*
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.neqabty.healthcare.R
import com.neqabty.healthcare.databinding.FragmentContactTermsBinding
import com.neqabty.healthcare.medicalnetwork.presentation.view.searchresult.*
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ContactTermsBottomSheet : BottomSheetDialogFragment() {
    @SuppressLint("RestrictedApi")
    var onActionListener: OnActionListener? = null

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        val layout = layoutInflater.inflate(R.layout.fragment_contact_terms, null)
        val binding = FragmentContactTermsBinding.inflate(
            layoutInflater,
            layout as ViewGroup,
            false
        )
        dialog.setContentView(binding.root)
        binding.backBtn.setOnClickListener {
            onActionListener?.onDismissListener()
            dialog.dismiss()
        }
        binding.closeBtn.setOnClickListener {
            onActionListener?.onDismissListener()
            dialog.dismiss()
        }
        binding.agreeBtn.setOnClickListener {
            onActionListener?.onAcceptListener()
            dialog.dismiss()
        }
    }

    interface OnActionListener {
        fun onAcceptListener()
        fun onDismissListener()
    }
}

