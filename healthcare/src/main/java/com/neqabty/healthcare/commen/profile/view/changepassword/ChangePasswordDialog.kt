package com.neqabty.healthcare.commen.profile.view.changepassword

import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.neqabty.healthcare.R
import com.neqabty.healthcare.commen.profile.view.profile.ProfileActivity
import com.neqabty.healthcare.databinding.FragmentChangePasswordDialogBinding
import com.neqabty.healthcare.mega.home.view.MegaHomeActivity


class ChangePasswordDialog : DialogFragment() {

    private var isHiddenOld = true
    private var isHiddenNew = true
    private var isHiddenConfirm = true
    private lateinit var binding: FragmentChangePasswordDialogBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        binding = FragmentChangePasswordDialogBinding.inflate(layoutInflater)
        return binding.root
    }

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

        binding.showHideOld.setOnClickListener { showHidePasswordOld() }
        binding.showHideNew.setOnClickListener { showHidePasswordNew() }
        binding.showHideConfirm.setOnClickListener { showHideConfirmPassword() }
    }

    private fun showHidePasswordOld() {
        if (isHiddenOld) {
            isHiddenOld = false
            binding.oldPassword.transformationMethod = null
            binding.showHideOld.setImageResource(R.drawable.ic_baseline_visibility_24)
        } else {
            isHiddenOld = true
            binding.oldPassword.transformationMethod = PasswordTransformationMethod()
            binding.showHideOld.setImageResource(R.drawable.ic_baseline_visibility_off_24)
        }
    }

    private fun showHidePasswordNew() {
        if (isHiddenNew) {
            isHiddenNew = false
            binding.newPassword.transformationMethod = null
            binding.showHideNew.setImageResource(R.drawable.ic_baseline_visibility_24)
        } else {
            isHiddenNew = true
            binding.newPassword.transformationMethod = PasswordTransformationMethod()
            binding.showHideNew.setImageResource(R.drawable.ic_baseline_visibility_off_24)
        }
    }

    private fun showHideConfirmPassword() {
        if (isHiddenConfirm) {
            isHiddenConfirm = false
            binding.confirmPassword.transformationMethod = null
            binding.showHideConfirm.setImageResource(R.drawable.ic_baseline_visibility_24)
        } else {
            isHiddenConfirm = true
            binding.confirmPassword.transformationMethod = PasswordTransformationMethod()
            binding.showHideConfirm.setImageResource(R.drawable.ic_baseline_visibility_off_24)
        }
    }

}