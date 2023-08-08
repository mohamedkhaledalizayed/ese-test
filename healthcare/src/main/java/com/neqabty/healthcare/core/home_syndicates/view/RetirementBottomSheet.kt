package com.neqabty.healthcare.core.home_syndicates.view

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.data.Constants
import com.neqabty.healthcare.core.syndicates.SyndicatesActivity
import com.neqabty.healthcare.databinding.FragmentRetirementBottomSheetBinding
import com.neqabty.healthcare.databinding.FragmentSyndicateServiceBottomSheetBinding
import java.text.FieldPosition


class RetirementBottomSheet : BottomSheetDialogFragment() {

    private var selectedService = ""
    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        val bottomSheetDialog = dialog as BottomSheetDialog

        val view = layoutInflater.inflate(R.layout.fragment_retirement_bottom_sheet, null)
        val binding = FragmentRetirementBottomSheetBinding.inflate(
            layoutInflater,
            view as ViewGroup,
            false
        )
        bottomSheetDialog.setContentView(binding.root)

        binding.spServices.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, l: Long) {
                when (position) {
                    1 -> {
                        selectedService = Constants.VISA
                    }
                    2 -> {
                        selectedService = Constants.BANK
                    }
                    else -> {

                    }
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }

        val activity = requireActivity() as SyndicatesHomeActivity
        binding.nextBtn.setOnClickListener {

            if (binding.etMembershipId.text.toString().isEmpty()){
                Toast.makeText(requireContext(), "من فضلك ادخل رقم العضوية.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (binding.etNationalId.text.toString().isEmpty()){
                Toast.makeText(requireContext(), "من فضلك ادخل الرقم القومى.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (binding.etNationalId.text.toString().length < 14){
                Toast.makeText(requireContext(), "من فضلك ادخل الرقم القومى بشكل صحيح.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (binding.spServices.selectedItemPosition == 0){
                Toast.makeText(requireContext(), "من فضلك إختر الخدمة اولا.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            activity.onNextClicked(binding.etMembershipId.text.toString(), binding.etNationalId.text.toString(), selectedService)
            dialog.dismiss()
        }

        binding.backBtn.setOnClickListener { dialog.dismiss() }

    }

}