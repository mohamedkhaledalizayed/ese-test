package com.neqabty.meganeqabty.profile.view.profile

import android.content.Intent
import android.os.Bundle
import com.neqabty.meganeqabty.R
import com.neqabty.meganeqabty.core.ui.BaseActivity
import com.neqabty.meganeqabty.databinding.ActivityProfileBinding
import com.neqabty.meganeqabty.profile.view.update.UpdateInfoActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActivity : BaseActivity<ActivityProfileBinding>() {

    override fun getViewBinding() = ActivityProfileBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        setupToolbar(title = "الصفحة الشخصية")

        binding.name.text = sharedPreferences.name
        binding.mobile.text = sharedPreferences.mobile
        binding.membershipNumber.text = ""
        binding.nationalId.text = sharedPreferences.nationalId

        binding.btnUpdate.setOnClickListener {
            val intent = Intent(this, UpdateInfoActivity::class.java)
            startActivity(intent)
        }
    }

}