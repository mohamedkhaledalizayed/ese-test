package com.neqabty.healthcare.commen.settings

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
import com.neqabty.healthcare.databinding.FragmentChangePasswordBottomSheetBinding


class ChangePasswordBottomSheet : BottomSheetDialogFragment() {



    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        val bottomSheetDialog = dialog as BottomSheetDialog

        val myDrawerView = layoutInflater.inflate(R.layout.fragment_change_password_bottom_sheet, null)
        val binding = FragmentChangePasswordBottomSheetBinding.inflate(
            layoutInflater,
            myDrawerView as ViewGroup,
            false
        )
        bottomSheetDialog.setContentView(binding.root)

        binding.closeBtn.setOnClickListener { dialog.dismiss() }

//        val activity = requireActivity() as ChefaaHomeActivity
//        binding.cameraContainer.setOnClickListener { activity.onCameraSelected() }
//        binding.galleryContainer.setOnClickListener { activity.onGallerySelected() }

    }

}