package com.neqabty.presentation.ui.intro

import androidx.lifecycle.ViewModelProvider
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
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
        adapter.addFragment(IntroPagerFragment.newInstance(R.drawable.intro_one, R.string.intro_one))
        adapter.addFragment(IntroPagerFragment.newInstance(R.drawable.intro_two, R.string.intro_two))
        adapter.addFragment(IntroPagerFragment.newInstance(R.drawable.intro_three, R.string.intro_three))
        adapter.addFragment(IntroPagerFragment.newInstance(R.drawable.intro_four, R.string.intro_four))
        adapter.addFragment(IntroPagerFragment.newInstance(R.drawable.intro_five, R.string.intro_five))

        binding.vpIntro.adapter = adapter
        binding.vpIntro.setDuration(10000)
//        binding.dotsIndicator.setViewPager(binding.vpIntro)

//        binding.vpIntro.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
//            override fun onPageScrollStateChanged(state: Int) {}
//            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
//            override fun onPageSelected(position: Int) {
// //                binding.tvTitle.setText(PagerModel.values()[position].titleResId)
//            }
//        })

        binding.bSkip.setOnClickListener {
            sharedPref.isIntroSkipped = true
            navigateToNext()
        }

//        binding.bNext.setOnClickListener {
//            if (vpIntro.currentItem == vpIntro.childCount - 1)
//                navigateToNext()
//            else
//                vpIntro.setCurrentItem(vpIntro.currentItem + 1, true)
//        }
    }

//region

    fun navigateToNext() {
        if (sharedPref.mobile.isEmpty())
            navController().navigate(R.id.openLoginFragment)
        else
            navController().navigate(R.id.openHomeFragment)
    }
// endregion

    fun navController() = findNavController()
}
