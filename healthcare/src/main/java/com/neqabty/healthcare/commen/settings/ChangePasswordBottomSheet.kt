package com.neqabty.healthcare.commen.settings

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.neqabty.healthcare.R
import com.neqabty.healthcare.auth.forgetpassword.data.model.ChangePasswordBody
import com.neqabty.healthcare.auth.forgetpassword.view.changepassword.ChangePasswordViewModel
import com.neqabty.healthcare.commen.profile.data.model.UpdatePasswordBody
import com.neqabty.healthcare.commen.profile.view.profile.ProfileViewModel
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.FragmentChangePasswordBottomSheetBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.cancel


@AndroidEntryPoint
class ChangePasswordBottomSheet : BottomSheetDialogFragment() {

    private val profileViewModel: ProfileViewModel by viewModels()
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

        binding.sendBtn.setOnClickListener {

            if (binding.password.text.toString().isNullOrEmpty()) {
                Toast.makeText(requireContext(), "من فضلك ادخل كلمة السر الحالية.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (binding.newPassword.text.toString().isNullOrEmpty()) {
                Toast.makeText(requireContext(), "من فضلك ادخل كلمة السر الجديدة.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (binding.confirmPassword.text.toString().isNullOrEmpty()) {
                Toast.makeText(requireContext(), "من فضلك ادخل تاكيد كلمة السر الجديدة.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (binding.newPassword.text.toString() != binding.confirmPassword.text.toString()) {
                Toast.makeText(requireContext(), "كلمة السر غير متطابقة.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            profileViewModel.updatePassword(UpdatePasswordBody(
                newPassword = binding.newPassword.text.toString(),
                oldPassword = binding.password.text.toString()
            ))

        }

        profileViewModel.password.observe(this) {
            it.let { resource ->
                when (resource.status) {

                    Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                        binding.layoutContainer.visibility = View.GONE
                    }
                    Status.SUCCESS -> {
                        binding.progressCircular.visibility = View.GONE
                        binding.layoutContainer.visibility = View.VISIBLE
                        dialog.dismiss()
                        Toast.makeText(requireContext(), resource.data, Toast.LENGTH_LONG).show()
                    }
                    Status.ERROR -> {
                        binding.progressCircular.visibility = View.GONE
                        dialog.dismiss()
                        Toast.makeText(requireContext(), "حدث خطاء اثناء تغيير كلمة المرور.", Toast.LENGTH_LONG).show()
                    }

                }
            }
        }

        binding.closeBtn.setOnClickListener { dialog.dismiss() }
        binding.backBtn.setOnClickListener { dialog.dismiss() }
        binding.newPassword.customSelectionActionModeCallback = actionMode
        binding.confirmPassword.customSelectionActionModeCallback = actionMode

    }

    val actionMode = object : ActionMode.Callback {
        override fun onCreateActionMode(p0: ActionMode?, p1: Menu?): Boolean {
            return false
        }

        override fun onPrepareActionMode(p0: ActionMode?, p1: Menu?): Boolean {
            return false
        }

        override fun onActionItemClicked(p0: ActionMode?, p1: MenuItem?): Boolean {
            return false
        }

        override fun onDestroyActionMode(p0: ActionMode?) {

        }
    }

}