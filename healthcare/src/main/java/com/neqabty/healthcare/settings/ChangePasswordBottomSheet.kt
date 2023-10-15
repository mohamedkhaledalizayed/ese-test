package com.neqabty.healthcare.settings

import android.annotation.SuppressLint
import android.app.Dialog
import android.text.method.PasswordTransformationMethod
import android.view.*
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.neqabty.healthcare.R
import com.neqabty.healthcare.databinding.FragmentChangePasswordBottomSheetBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.regex.Matcher
import java.util.regex.Pattern


@AndroidEntryPoint
class ChangePasswordBottomSheet : BottomSheetDialogFragment() {

    private var isHiddenOld = true
    private var isHiddenNew = true
    private var isHiddenConfirm = true
    private lateinit var binding: FragmentChangePasswordBottomSheetBinding
    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        val bottomSheetDialog = dialog as BottomSheetDialog

        val myDrawerView = layoutInflater.inflate(R.layout.fragment_change_password_bottom_sheet, null)
        binding = FragmentChangePasswordBottomSheetBinding.inflate(
            layoutInflater,
            myDrawerView as ViewGroup,
            false
        )
        bottomSheetDialog.setContentView(binding.root)

        binding.sendBtn.setOnClickListener {

            if (binding.oldPassword.text.toString().isNullOrEmpty()) {
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

            if (!isValidPassword(binding.newPassword.text.toString().trim())) {
                Toast.makeText(requireActivity(), getString(R.string.password_conditions), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val activity = requireActivity() as SettingsActivity
            activity.onChangeClicked(
                newPassword = binding.newPassword.text.toString(),
                oldPassword = binding.oldPassword.text.toString())
            dismiss()
        }

        binding.closeBtn.setOnClickListener { dismiss() }
        binding.backBtn.setOnClickListener { dismiss() }
        binding.newPassword.customSelectionActionModeCallback = actionMode
        binding.confirmPassword.customSelectionActionModeCallback = actionMode

        binding.showHideOld.setOnClickListener { showHidePasswordOld() }
        binding.showHideNew.setOnClickListener { showHidePasswordNew() }
        binding.showHideConfirm.setOnClickListener { showHideConfirmPassword() }

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

    private fun isValidPassword(password: String?): Boolean {
        if (password?.length!! < 8)
            return false
        val pattern: Pattern
        val matcher: Matcher
        val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"
        pattern = Pattern.compile(PASSWORD_PATTERN)
        matcher = pattern.matcher(password)
        return matcher.matches()
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