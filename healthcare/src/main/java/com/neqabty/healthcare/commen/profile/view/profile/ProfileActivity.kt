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
import com.neqabty.healthcare.commen.profile.view.personalinfo.PersonalInfoActivity
import com.neqabty.healthcare.databinding.ActivityProfileMegaBinding
import com.neqabty.healthcare.commen.profile.view.update.UpdateInfoActivity
import com.neqabty.healthcare.commen.settings.SettingsActivity
import com.neqabty.healthcare.core.packages.PackagesActivity
import com.neqabty.healthcare.core.utils.ErrorBody
import com.neqabty.healthcare.mega.home.view.SuggestionDialog
import com.neqabty.healthcare.sustainablehealth.suggestions.presentation.SuggestionsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActivity : BaseActivity<ActivityProfileMegaBinding>() {

    private val profileViewModel: ProfileViewModel by viewModels()
    override fun getViewBinding() = ActivityProfileMegaBinding.inflate(layoutInflater)
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

//        setupToolbar(titleResId = R.string.profile)
//
//
        profileViewModel.getUserProfile("Token ${sharedPreferences.token}")
        profileViewModel.user.observe(this) {

            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
//                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {

                        binding.userName.text = resource.data!!.data.fullName
                        binding.membershipIdValue.text = "${resource.data.data.membershipId}"
                        if (sharedPreferences.isSyndicateMember) {
                            binding.contact.visibility = View .GONE
                        }
//                        binding.progressCircular.visibility = View.GONE
//                        binding.layoutContainer.visibility = View.VISIBLE
//
//                        binding.name.text = resource.data?.data?.fullName ?: ""
//                        binding.mobile.text = resource.data?.data?.mobile
//                        binding.phone.text = resource.data?.data?.mobile
//                        binding.membershipNumber.text = resource.data?.data?.membershipId.toString()
//                        binding.nationalId.text = resource.data?.data?.nationalId.toString()
//                        binding.email.text = resource.data?.data?.email ?: ""
//                        binding.syndicate.text = resource.data?.data?.entity?.name
                    }
                    Status.ERROR -> {
//                        binding.progressCircular.visibility = View.GONE
                    }
                }
            }

        }

        binding.profile.setOnClickListener { startActivity(Intent(this, PersonalInfoActivity::class.java))  }
        binding.cards.setOnClickListener {  }
        binding.contact.setOnClickListener {  }
        binding.packages.setOnClickListener { startActivity(Intent(this, PackagesActivity::class.java)) }
        binding.settings.setOnClickListener { startActivity(Intent(this, SettingsActivity::class.java)) }
        binding.support.setOnClickListener { startActivity(Intent(this, SuggestionsActivity::class.java)) }

    }

}