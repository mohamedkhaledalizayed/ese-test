package com.neqabty.healthcare.sustainablehealth.profile.view.profile


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.google.gson.Gson
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.R
import com.neqabty.healthcare.sustainablehealth.profile.data.model.UpdatePasswordBody
import com.neqabty.healthcare.sustainablehealth.profile.view.changepassword.ChangePasswordDialog
import com.neqabty.healthcare.sustainablehealth.profile.view.model.PasswordError
import com.neqabty.healthcare.databinding.ActivityProfileMegaBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActivity : BaseActivity<ActivityProfileMegaBinding>(), IOnUpdateClickListener {

    private val profileViewModel: ProfileViewModel by viewModels()
    override fun getViewBinding() = ActivityProfileMegaBinding.inflate(layoutInflater)
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        setupToolbar(titleResId = R.string.profile)


        profileViewModel.user.observe(this) {

            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.progressCircular.visibility = View.GONE
                        binding.layoutContainer.visibility = View.VISIBLE

                        binding.name.text = resource.data?.data?.fullName ?: ""
                        binding.mobile.text = resource.data?.data?.mobile
                        binding.phone.text = resource.data?.data?.mobile
                        binding.nationalId.text = resource.data?.data?.nationalId.toString()
                        binding.email.text = resource.data?.data?.email ?: ""
                        binding.syndicate.text = resource.data?.data?.entity?.name
                    }
                    Status.ERROR -> {
                        binding.progressCircular.visibility = View.GONE
                    }
                }
            }


        }


        profileViewModel.password.observe(this) {

            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.progressCircular.visibility = View.GONE
                        Toast.makeText(this, it.data, Toast.LENGTH_LONG).show()
                    }
                    Status.ERROR -> {
                        binding.progressCircular.visibility = View.GONE
                        val error = Gson().fromJson(resource.message.toString(), PasswordError::class.java)

                        Toast.makeText(this, error.error, Toast.LENGTH_LONG).show()
                    }
                }
            }


        }

        binding.changePassword.setOnClickListener {
            val fm: FragmentManager = supportFragmentManager
            val dialog = ChangePasswordDialog()
            dialog.show(fm, "")
            dialog.setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth)
        }
    }

    override fun onResume() {
        super.onResume()
        profileViewModel.getUserProfile("Token ${sharedPreferences.token}")
    }

    override fun onClick(oldPassword: String, newPassword: String) {
        profileViewModel.updatePassword(UpdatePasswordBody(oldPassword = oldPassword, newPassword = newPassword))
    }

}