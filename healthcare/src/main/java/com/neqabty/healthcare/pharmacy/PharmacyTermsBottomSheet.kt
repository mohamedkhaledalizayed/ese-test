package com.neqabty.healthcare.pharmacy

import android.annotation.SuppressLint
import android.app.Dialog
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.neqabty.healthcare.R
import com.neqabty.healthcare.databinding.FragmentPharmacyTermsBottomSheetBinding


class PharmacyTermsBottomSheet : BottomSheetDialogFragment() {

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        val bottomSheetDialog = dialog as BottomSheetDialog

        val myDrawerView = layoutInflater.inflate(R.layout.fragment_pharmacy_terms_bottom_sheet, null)
        val binding = FragmentPharmacyTermsBottomSheetBinding.inflate(
            layoutInflater,
            myDrawerView as ViewGroup,
            false
        )
        bottomSheetDialog.setContentView(binding.root)

        binding.closeBtn.setOnClickListener { dialog.dismiss() }

        val activity = requireActivity() as PharmacyActivity
        binding.agreeBtn.setOnClickListener {
            activity.onAgreeClicked()
            dialog.dismiss()
        }
        binding.backBtn.setOnClickListener { dialog.dismiss() }

    }

}