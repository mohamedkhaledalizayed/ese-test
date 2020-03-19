package com.neqabty.presentation.ui.intro

import android.arch.lifecycle.ViewModelProvider
import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.neqabty.R
import com.neqabty.databinding.IntroFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.ui.common.CustomFragmentPagerAdapter
import com.neqabty.presentation.util.PreferencesHelper
import com.neqabty.presentation.util.autoCleared
import kotlinx.android.synthetic.main.intro_fragment.*
import javax.inject.Inject

class IntroFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<IntroFragmentBinding>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setupToolbar(false)
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.intro_fragment,
                container,
                false,
                dataBindingComponent
        )

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initializeViews()
    }

    fun initializeViews() {

        val adapter = CustomFragmentPagerAdapter(childFragmentManager)
        adapter.addFragment(IntroPagerFragment.newInstance(R.drawable.logo, R.string.loading))
        adapter.addFragment(IntroPagerFragment.newInstance(R.mipmap.btn, R.string.congratulations))
        adapter.addFragment(IntroPagerFragment.newInstance(R.mipmap.card_2, R.string.request_sent))

        binding.vpIntro.adapter = adapter
        binding.vpIntro.setSwipePagingEnabled(false)
        binding.vpIntro.offscreenPageLimit = 2
        binding.dotsIndicator.setViewPager(binding.vpIntro)

        binding.vpIntro.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {
//                binding.tvTitle.setText(PagerModel.values()[position].titleResId)
            }
        })

        binding.bSkip.setOnClickListener {
            PreferencesHelper(requireContext()).isIntroSkipped = true
            navController().navigate(IntroFragmentDirections.openLoginFragment())
        }

        binding.bContinue.setOnClickListener {
            if (vpIntro.currentItem == vpIntro.childCount - 1)
                navController().navigate(IntroFragmentDirections.openLoginFragment())
            else
                vpIntro.setCurrentItem(vpIntro.currentItem + 1, true)
        }

    }

//region

// endregion

    fun navController() = findNavController()
}
