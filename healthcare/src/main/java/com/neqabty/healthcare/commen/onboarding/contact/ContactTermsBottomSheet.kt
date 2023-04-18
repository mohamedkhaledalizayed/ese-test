package com.neqabty.healthcare.commen.onboarding.contact


import android.annotation.SuppressLint
import android.app.Dialog
import android.view.ViewGroup
import android.widget.*
import com.neqabty.healthcare.R
import com.neqabty.healthcare.databinding.FragmentContactTermsBinding
import com.neqabty.healthcare.databinding.FragmentFilterBottomSheetBinding
import com.neqabty.healthcare.sustainablehealth.search.presentation.view.searchresult.*
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ContactTermsBottomSheet : RoundedBottomSheetDialogFragment() {
    @SuppressLint("RestrictedApi")
    var onActionListener: OnActionListener? = null

    override fun setupDialog(dialog: Dialog, style: Int) {
        val layout = layoutInflater.inflate(R.layout.fragment_contact_terms, null)
        val binding = FragmentContactTermsBinding.inflate(
            layoutInflater,
            layout as ViewGroup,
            false
        )
        dialog.setContentView(binding.root)
        binding.bBack.setOnClickListener {
            onActionListener?.onDismissListener()
            dialog.dismiss()
        }
        binding.bAccept.setOnClickListener {
            onActionListener?.onAcceptListener()
            dialog.dismiss()
        }
    }

    interface OnActionListener {
        fun onAcceptListener()
        fun onDismissListener()
    }
}

