package com.neqabty.healthcare.chefaa.home.view

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

import com.neqabty.healthcare.R
import com.neqabty.healthcare.chefaa.orders.presentation.orderbynote.OrderByNoteActivity
import com.neqabty.healthcare.contactus.NumbersDialog
import com.neqabty.healthcare.databinding.FragmentPickUpImageBottomSheetBinding


class PickUpImageBottomSheet : BottomSheetDialogFragment() {


    var fromHome = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            fromHome = it.getBoolean("tag")
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(tag: Boolean) =
            PickUpImageBottomSheet().apply {
                arguments = Bundle().apply {
                    putBoolean("tag", tag)
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


        val activity = if(fromHome){
            requireActivity() as ChefaaHomeActivity
        }else{
            requireActivity() as OrderByNoteActivity
        }
        binding.cameraContainer.setOnClickListener { activity.onCameraSelected() }
        binding.galleryContainer.setOnClickListener { activity.onGallerySelected() }

    }

}