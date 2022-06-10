package com.neqabty.healthcare.modules.search.presentation.view.filter

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.neqabty.healthcare.R
import com.neqabty.healthcare.modules.search.presentation.view.searchresult.SearchResultActivity


class FilterBottomSheet : RoundedBottomSheetDialogFragment() {

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        val bottomSheetDialog = dialog as BottomSheetDialog
        bottomSheetDialog.setContentView(R.layout.fragment_filter_bottom_sheet)

        val filterButton = dialog.findViewById<TextView>(R.id.filter_btn)

        filterButton?.setOnClickListener {
            val searchActivity = activity as SearchResultActivity
            searchActivity.onFilterClicked("الجيزة","الهرم")
            dismiss()
        }
        try {
            val behaviorField =
                bottomSheetDialog.javaClass.getDeclaredField("behavior")
            behaviorField.isAccessible = true
            val behavior =
                behaviorField[bottomSheetDialog] as BottomSheetBehavior<*>
            behavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(
                    bottomSheet: View,
                    newState: Int
                ) {
                    if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                        behavior.state = BottomSheetBehavior.STATE_EXPANDED
                    }
                }

                override fun onSlide(
                    bottomSheet:View,
                    slideOffset: Float
                ) {
                }
            })
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
    }
}

