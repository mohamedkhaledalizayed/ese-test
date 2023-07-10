package com.neqabty.healthcare.commen.profile.view.profile


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.neqabty.healthcare.commen.profile.domain.entity.profile.ProfileEntity
import com.neqabty.healthcare.commen.profile.view.personalinfo.PersonalInfoActivity
import com.neqabty.healthcare.commen.settings.SettingsActivity
import com.neqabty.healthcare.commen.splash.view.SplashActivity
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.PushNotificationsWrapper
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivityProfileMegaBinding
import com.neqabty.healthcare.commen.mypackages.packages.view.MyPackagesActivity
import com.neqabty.healthcare.sustainablehealth.suggestions.presentation.SuggestionsActivity
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActivity : BaseActivity<ActivityProfileMegaBinding>() {

    private lateinit var profileEntity: ProfileEntity
    private val profileViewModel: ProfileViewModel by viewModels()
    override fun getViewBinding() = ActivityProfileMegaBinding.inflate(layoutInflater)

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        profileViewModel.user.observe(this) {

            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        showProgressDialog()
                    }
                    Status.SUCCESS -> {
                        hideProgressDialog()
                        profileEntity = resource.data!!
                        binding.userName.text = resource.data.data.fullName
                        if (sharedPreferences.isSyndicateMember) {
                            binding.membershipIdValue.text = "${resource.data.data.membershipId}"
                            binding.contact.visibility = View.GONE
                            binding.syndicateContainer.visibility = View.VISIBLE
                            Picasso.get().load(resource.data.data.qrImage).into(binding.qrCode)
                        } else {
                            binding.neqabtyQrCode.visibility = View.VISIBLE
                            Picasso.get().load(resource.data.data.qrImage).into(binding.neqabtyQrCode)
                        }
                    }
                    Status.ERROR -> {
                        hideProgressDialog()
                    }
                }
            }

        }

        binding.headerContainer.setOnClickListener { finish() }

        binding.profile.setOnClickListener {
            val intent = Intent(this, PersonalInfoActivity::class.java)
            intent.putExtra("data", profileEntity)
            startActivity(intent)
        }
        binding.cards.setOnClickListener { }
        binding.contact.setOnClickListener { }
        binding.packages.setOnClickListener {
            startActivity(Intent(this, MyPackagesActivity::class.java))
        }
        binding.settings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
        binding.support.setOnClickListener {
            startActivity(Intent(this, SuggestionsActivity::class.java))
        }
        binding.logout.setOnClickListener {
            sharedPreferences.clearAll()
            PushNotificationsWrapper().deleteToken(this)
            startActivity(Intent(this, SplashActivity::class.java))
            finishAffinity()
        }

    }

    override fun onResume() {
        super.onResume()
        profileViewModel.getUserProfile("Token ${sharedPreferences.token}")
    }

}