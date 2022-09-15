package com.neqabty.healthcare.modules.profile.presentation


import android.os.Bundle
import android.view.View
import com.neqabty.core.utils.Status
import com.neqabty.healthcare.databinding.ActivityProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.viewModels
import com.neqabty.core.ui.BaseActivity
import com.neqabty.core.utils.loadSVG

@AndroidEntryPoint
class ProfileActivity : BaseActivity<ActivityProfileBinding>() {


    private val mAdapter = PackagesAdapter()
    private val profileViewModel: ProfileViewModel by viewModels()
    override fun getViewBinding() = ActivityProfileBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(title = "ملفي الشخصي")


        binding.followersRecycler.adapter = mAdapter

        profileViewModel.getProfile(sharedPreferences.mobile)
        profileViewModel.userData.observe(this){
            it.let { resource ->

            when(resource.status){
                Status.LOADING ->  {
                    binding.progressCircular.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    binding.progressCircular.visibility = View.GONE
                    if (resource.data!!.status){
                        binding.layoutContainer.visibility = View.VISIBLE
                        binding.fullName.text = "${resource.data?.data?.client?.name}"
                        binding.name.text = "${resource.data?.data?.client?.name}"
                        binding.email.text = "${resource.data?.data?.client?.email}"
                        binding.phone.text = "${resource.data?.data?.client?.mobile}"
                        binding.birthDate.text = ""
                        binding.address.text = "${resource.data?.data?.client?.address}"
                        binding.job.text = "${resource.data?.data?.client?.job}"
                        binding.nationalId.text = "${resource.data?.data?.client?.nationalId}"
                        mAdapter.submitList(resource.data!!.data.subscribedPackages)
                        binding.qrCode.loadSVG(resource.data!!.data.client.qrCode)
                    }
                }
                Status.ERROR ->{
                    binding.progressCircular.visibility = View.GONE
                }
            }

            }
        }


    }
}