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

        binding.name.text = sharedPreferences.name
        binding.mobile.text = sharedPreferences.mobile
        binding.membershipNumber.text = ""
        binding.nationalId.text = sharedPreferences.nationalId

        profileViewModel.user.observe(this) {

            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                    }
                    Status.SUCCESS -> {


                    }
                    Status.ERROR -> {


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