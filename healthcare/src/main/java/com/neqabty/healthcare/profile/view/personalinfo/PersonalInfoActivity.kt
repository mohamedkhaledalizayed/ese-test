package com.neqabty.healthcare.profile.view.personalinfo


import android.os.Bundle
import android.view.View
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.databinding.ActivityPersonalInfoBinding
import com.neqabty.healthcare.profile.domain.entity.profile.ProfileEntity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PersonalInfoActivity : BaseActivity<ActivityPersonalInfoBinding>() {

    override fun getViewBinding() = ActivityPersonalInfoBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(title = "البيانات الشخصية")
        val data = intent.extras?.getParcelable<ProfileEntity>("data")!!

        data.data.let {
            binding.etName.setText(it.fullName ?: "")
            binding.etEmail.setText(it.email ?: "")
            binding.etPhone.setText(it.mobile ?: "")
            if (it.entity.type == "owner"){
                binding.syndicate.text = "نقابتى"
                binding.membershipId.visibility = View.GONE
            }else{
                binding.syndicate.text = it.entity.name
                binding.membershipId.text = "${it.membershipId}"
            }

            binding.etAddress.setText(it.address ?: "")
        }

    }
}