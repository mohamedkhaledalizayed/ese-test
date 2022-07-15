package com.neqabty.healthcare.modules.profile.presentation


import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.databinding.ActivityProfileBinding
import com.neqabty.healthcare.modules.subscribtions.data.model.Followers
import com.neqabty.healthcare.modules.subscribtions.presentation.view.FollowerAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProfileActivity : BaseActivity<ActivityProfileBinding>() {


    private val mAdapter = FollowerAdapter()
    private var listFollower = mutableListOf<Followers>()
    private val profileViewModel: ProfileViewModel by viewModels()
    override fun getViewBinding() = ActivityProfileBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(title = "ملفي الشخصي")


        profileViewModel.getProfile("01000765578")
        val follower = Followers(
            name = "شادي عبد الله محمد",
            image = "",
            national_id = "٢٩٢٠٢٢٢٠١٠١٤٣٠",
            relation_type = 0
        )

        listFollower.add(follower)
        listFollower.add(follower)
        binding.followersRecycler.adapter = mAdapter
        mAdapter.submitList(listFollower)
    }
}