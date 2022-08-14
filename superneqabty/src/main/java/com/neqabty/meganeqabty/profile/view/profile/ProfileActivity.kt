package com.neqabty.meganeqabty.profile.view.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.neqabty.core.ui.BaseActivity
import com.neqabty.core.utils.Status
import com.neqabty.meganeqabty.R
import com.neqabty.meganeqabty.databinding.ActivityProfileMegaBinding
import com.neqabty.meganeqabty.profile.view.update.UpdateInfoActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActivity : BaseActivity<ActivityProfileMegaBinding>() {

    private val profileViewModel: ProfileViewModel by viewModels()
    override fun getViewBinding() = ActivityProfileMegaBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        setupToolbar(titleResId = R.string.profile)

        profileViewModel.getUserProfile("Token ${sharedPreferences.token}")
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
                        binding.membershipNumber.text = ""
                        binding.nationalId.text = resource.data?.data?.nationalId.toString()
                        binding.email.text = resource.data?.data?.email ?: ""
                        binding.syndicate.text = resource.data?.data?.entity?.name
                    }
                    Status.ERROR -> {
                        binding.progressCircular.visibility = View.GONE
                        Log.e("jhfdsasdf", resource.message.toString())
                    }
                }
            }


        }

        binding.btnUpdate.setOnClickListener {
            val intent = Intent(this, UpdateInfoActivity::class.java)
            startActivity(intent)
        }
    }

}