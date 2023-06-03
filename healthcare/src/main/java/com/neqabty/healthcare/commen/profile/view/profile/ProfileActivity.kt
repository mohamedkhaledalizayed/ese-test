package com.neqabty.healthcare.commen.profile.view.profile


import android.annotation.SuppressLint
import android.content.Intent
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
import com.neqabty.healthcare.commen.profile.data.model.UpdatePasswordBody
import com.neqabty.healthcare.commen.profile.view.changepassword.ChangePasswordDialog
import com.neqabty.healthcare.commen.profile.view.model.PasswordError
import com.neqabty.healthcare.databinding.ActivityProfileMegaBinding
import com.neqabty.healthcare.commen.profile.view.update.UpdateInfoActivity
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
                        binding.membershipNumber.text = resource.data?.data?.membershipId.toString()
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


        profileViewModel.cardStatus.observe(this) {

            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.progressCircular.visibility = View.GONE
                        binding.mobileNumber.text = resources.getString(R.string.phone_number) +
                                " ${resource.data?.mobile}"
                        binding.address.text = resources.getString(R.string.address_) +
                                " ${resource.data?.address}"
                        binding.year.text = resources.getString(R.string.year_) +
                                " ${resource.data?.year}"
                        binding.message.text = resources.getString(R.string.order_status) +
                                " ${resource.data?.statusMessage}"
                        binding.cardBtn.visibility = View.GONE
                        if (resource.data?.statusCode == 3){
                            binding.cardBtn.visibility = View.VISIBLE
                        }
                    }
                    Status.ERROR -> {
                        binding.progressCircular.visibility = View.GONE
                        if (resource.message == "404"){
                            binding.mobileNumber.visibility = View.GONE
                            binding.address.visibility = View.GONE
                            binding.year.visibility = View.GONE
                            binding.cardBtn.visibility = View.VISIBLE
                            binding.message.text = getString(R.string.card_request_not_found)
                        }
                    }
                }
            }


        }


        profileViewModel.licenceStatus.observe(this) {

            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.progressCircular.visibility = View.GONE
                        binding.licenceMessage.text = resources.getString(R.string.order_status) +
                                " ${resource.data?.statusMessage}"
                        binding.licenceBtn.visibility = View.GONE
                        if (resource.data?.statusCode == 3){
                            binding.licenceBtn.visibility = View.VISIBLE
                        }
                    }
                    Status.ERROR -> {
                        binding.progressCircular.visibility = View.GONE
                        if (resource.message == "404"){
                            binding.licenceBtn.visibility = View.VISIBLE
                            binding.licenceMessage.text = getString(R.string.licence_not_found)
                        }
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

        binding.cardBtn.setOnClickListener {
            val intent = Intent(this, UpdateInfoActivity::class.java)
            intent.putExtra("key", 200)
            startActivity(intent)
        }

        binding.licenceBtn.setOnClickListener {
            val intent = Intent(this, UpdateInfoActivity::class.java)
            intent.putExtra("key", 100)
            startActivity(intent)
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
        if (!intent.getBooleanExtra("healthcare", false)){
            profileViewModel.membershipCardStatus()
            profileViewModel.getLicenseStatus()
        }else{
            binding.updateLicence.visibility = View.GONE
            binding.cardRequest.visibility = View.GONE
            binding.membershipIdLayout.visibility = View.GONE
            binding.view.visibility = View.GONE
        }
    }

    override fun onClick(oldPassword: String, newPassword: String) {
        profileViewModel.updatePassword(UpdatePasswordBody(oldPassword = oldPassword, newPassword = newPassword))
    }

}