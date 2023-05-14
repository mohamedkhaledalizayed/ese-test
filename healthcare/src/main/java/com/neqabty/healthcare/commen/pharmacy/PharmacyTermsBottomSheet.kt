package com.neqabty.healthcare.commen.pharmacy

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.neqabty.healthcare.R
import com.neqabty.healthcare.chefaa.home.presentation.homescreen.ChefaaHomeActivity
import com.neqabty.healthcare.databinding.FragmentPharmacyTermsBottomSheetBinding
import com.neqabty.healthcare.databinding.FragmentPickUpImageBottomSheetBinding


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