package com.neqabty.healthcare.profile.view.profile


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.more.view.MoreActivity
import com.neqabty.healthcare.core.syndicates.SyndicatesActivity
import com.neqabty.healthcare.core.ui.AuthDialog
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.ui.ExitDialog
import com.neqabty.healthcare.core.utils.PushNotificationsWrapper
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivityProfileMegaBinding
import com.neqabty.healthcare.invoices.view.InvoicesActivity
import com.neqabty.healthcare.mypackages.packages.view.MyPackagesActivity
import com.neqabty.healthcare.onboarding.signup.view.SignupActivity
import com.neqabty.healthcare.profile.domain.entity.profile.ProfileEntity
import com.neqabty.healthcare.profile.view.personalinfo.PersonalInfoActivity
import com.neqabty.healthcare.settings.SettingsActivity
import com.neqabty.healthcare.splash.view.SplashActivity
import com.neqabty.healthcare.suggestions.presentation.SuggestionsActivity
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
        if (!sharedPreferences.isAuthenticated){
            binding.qrCodeContainer.visibility = View.GONE
        }
        binding.bnvSyndicatesHome.menu.setGroupCheckable(0, false, true)
        binding.bnvSyndicatesHome.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    finish()
                    true
                }
                R.id.navigation_syndicates -> {
                    val intent = Intent(this, SyndicatesActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.navigation_payments -> {
                    val intent = Intent(this, InvoicesActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.navigation_more -> {
                    val intent = Intent(this, MoreActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                else -> false
            }
        }

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
                        binding.qrCodeContainer.visibility = View.GONE
                        hideProgressDialog()
                    }
                }
            }

        }

        binding.headerContainer.setOnClickListener { finish() }

        binding.profile.setOnClickListener {
            if (sharedPreferences.isAuthenticated){
                val intent = Intent(this, PersonalInfoActivity::class.java)
                intent.putExtra("data", profileEntity)
                startActivity(intent)
            }else{
                askForLogin()
            }
        }
        binding.cards.setOnClickListener { }
        binding.contact.setOnClickListener { }
        binding.packages.setOnClickListener {
            if (sharedPreferences.isAuthenticated){
                startActivity(Intent(this, MyPackagesActivity::class.java))
            }else{
                askForLogin()
            }
        }
        binding.settings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
        binding.support.setOnClickListener {
            startActivity(Intent(this, SuggestionsActivity::class.java))
        }
        binding.logout.setOnClickListener {
            if (sharedPreferences.isAuthenticated){
                sharedPreferences.clearAll()
                PushNotificationsWrapper().deleteToken(this)
                startActivity(Intent(this, SplashActivity::class.java))
                finishAffinity()
            }else{
                sharedPreferences.clearAll()
                startActivity(Intent(this, SignupActivity::class.java))
                finishAffinity()
            }

        }

    }

    override fun onResume() {
        super.onResume()
        if (!sharedPreferences.isAuthenticated){
            binding.logoutText.text = "تسجيل دخول"
        }
        profileViewModel.getUserProfile("Token ${sharedPreferences.token}")
    }

}