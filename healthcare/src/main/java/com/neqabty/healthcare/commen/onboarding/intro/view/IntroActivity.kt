package com.neqabty.healthcare.commen.onboarding.intro.view

import android.content.Intent
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.databinding.ActivityIntroBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroActivity : BaseActivity<ActivityIntroBinding>() {
    override fun getViewBinding() = ActivityIntroBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initializeViews()
    }

    private fun initializeViews() {
        val introList = mutableListOf<IntroPagerModel>()
        introList.add(IntroPagerModel(R.string.intro_three, R.drawable.ic_intro_three))
        introList.add(IntroPagerModel(R.string.intro_two, R.drawable.ic_intro_two))
        introList.add(IntroPagerModel(R.string.intro_one, R.drawable.ic_intro_one))

        val adapter = IntroFragmentPagerAdapter(supportFragmentManager)
        for (item in introList)
            adapter.addFragment(IntroPagerFragment.newInstance(item))

        binding.vpIntro.adapter = adapter
        binding.vpIntro.setAutoScrollEnabled(false)
        binding.ciIntro.setViewPager(binding.vpIntro)

        binding.tvDescription.text = getString(introList[0].titleResId)

        binding.vpIntro.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                binding.tvDescription.text = getString(introList[position].titleResId)
            }
        })

        binding.tvSkip.setOnClickListener {
            navigate()
        }

        binding.ibNext.setOnClickListener {
            if (binding.vpIntro.currentItem == 0)
                navigate()
            else
                binding.vpIntro.setCurrentItem(binding.vpIntro.currentItem - 1, true)
        }

        binding.vpIntro.currentItem = 3
    }

//region

    private fun navigate() {
        sharedPreferences.isIntroSkipped = true
        val mainIntent = Intent(this, getTheNextActivityFromIntro())
        startActivity(mainIntent)
        finish()
    }
// endregion
}