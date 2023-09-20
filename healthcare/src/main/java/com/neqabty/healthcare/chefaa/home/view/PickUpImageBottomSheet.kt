package com.neqabty.healthcare.chefaa.home.view

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

import com.neqabty.healthcare.R
import com.neqabty.healthcare.chefaa.orders.presentation.orderbynote.OrderByNoteActivity
import com.neqabty.healthcare.databinding.FragmentPickUpImageBottomSheetBinding
import com.neqabty.healthcare.pharmacymart.orders.ui.uploadprescription.PharmacyMartCartActivity
import com.neqabty.healthcare.pharmacymart.home.ui.PharmacyMartHomeActivity


class PickUpImageBottomSheet : BottomSheetDialogFragment() {


    var from = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            from = it.getInt("tag")
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(tag: Int) =
            PickUpImageBottomSheet().apply {
                arguments = Bundle().apply {
                    putInt("tag", tag)
                }
            }
    }

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        val bottomSheetDialog = dialog as BottomSheetDialog

        val myDrawerView = layoutInflater.inflate(R.layout.fragment_pick_up_image_bottom_sheet, null)
        val binding = FragmentPickUpImageBottomSheetBinding.inflate(
            layoutInflater,
            myDrawerView as ViewGroup,
            false
        )
        bottomSheetDialog.setContentView(binding.root)

        binding.closeBtn.setOnClickListener { dialog.dismiss() }

        var activity = when (from) {
            0 -> {
                requireActivity() as PharmacyMartHomeActivity
            }
            1 -> {
                requireActivity() as OrderByNoteActivity
            }
            2 -> {
                requireActivity() as PharmacyMartCartActivity
            }
            else -> {
                requireActivity() as ChefaaHomeActivity
            }
        }

        binding.cameraContainer.setOnClickListener { activity.onCameraSelected() }
        binding.galleryContainer.setOnClickListener { activity.onGallerySelected() }

    }

}