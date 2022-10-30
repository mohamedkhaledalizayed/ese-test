package com.neqabty.shealth.sustainablehealth.profile.view.changepassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.neqabty.shealth.sustainablehealth.profile.view.profile.ProfileActivity
import com.neqabty.shealth.databinding.FragmentChangePasswordDialogBinding


class ChangePasswordDialog : DialogFragment() {

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
    }

}