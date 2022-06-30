package com.neqabty.meganeqabty.profile.view.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.neqabty.meganeqabty.R
import com.neqabty.meganeqabty.core.ui.BaseActivity
import com.neqabty.meganeqabty.core.utils.Status
import com.neqabty.meganeqabty.databinding.ActivityProfileBinding
import com.neqabty.meganeqabty.profile.view.update.UpdateInfoActivity
import com.neqabty.meganeqabty.profile.view.update.UpdateProfileViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActivity : BaseActivity<ActivityProfileBinding>() {

    private val profileViewModel: ProfileViewModel by viewModels()
    override fun getViewBinding() = ActivityProfileBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        setupToolbar(title = "الصفحة الشخصية")

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

                        binding.name.text = resource.data?.name
                        binding.mobile.text = resource.data?.account?.mobile
                        binding.membershipNumber.text = resource.data?.membershipId.toString()
                        binding.nationalId.text = resource.data?.nationalId.toString()
                        binding.email.text = resource.data?.account?.email
                        binding.syndicate.text = resource.data?.entity?.name
                    }
                    Status.ERROR -> {
                        binding.progressCircular.visibility = View.GONE

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