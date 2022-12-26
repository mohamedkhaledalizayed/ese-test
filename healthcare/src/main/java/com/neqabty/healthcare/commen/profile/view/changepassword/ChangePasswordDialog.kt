package com.neqabty.healthcare.commen.profile.view.changepassword

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.neqabty.healthcare.commen.profile.view.profile.ProfileActivity
import com.neqabty.healthcare.core.ui.BaseDialogFragment
import com.neqabty.healthcare.databinding.FragmentChangePasswordDialogBinding


class ChangePasswordDialog : BaseDialogFragment<FragmentChangePasswordDialogBinding>() {

    override fun getViewBinding() = FragmentChangePasswordDialogBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.changePassword.setOnClickListener {
            if (binding.oldPassword.text.toString().isNullOrEmpty()){
                Toast.makeText(requireContext(), "من فضلك ادخل كلمة المرور الحالية.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (binding.newPassword.text.toString().isNullOrEmpty()){
                Toast.makeText(requireContext(), "من فضلك ادخل كلمة المرور الجديدة.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (binding.confirmPassword.text.toString().isNullOrEmpty()){
                Toast.makeText(requireContext(), "من فضلك ادخل تاكيد كلمة المرور.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (binding.confirmPassword.text.toString() != binding.newPassword.text.toString()){
                Toast.makeText(requireContext(), "كلمة السر غير متطابقة.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val activity = activity as ProfileActivity
            activity.onClick(
                oldPassword = binding.oldPassword.text.toString(),
                newPassword = binding.newPassword.text.toString()
            )
            dismiss()


        }
    }

}