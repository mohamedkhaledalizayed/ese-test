package com.neqabty.presentation.ui.claiming

import androidx.fragment.app.viewModels
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.neqabty.R
import com.neqabty.databinding.ClaimingFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.ui.common.CustomFragmentPagerAdapter
import com.neqabty.presentation.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClaimingFragment : BaseFragment() {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<ClaimingFragmentBinding>()

    private val claimingViewModel: ClaimingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.claiming_fragment,
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
        adapter.addFragment(ClaimingStep1Fragment())
        adapter.addFragment(ClaimingStep2Fragment())
        adapter.addFragment(ClaimingStep3Fragment())
//        adapter.addFragment(ClaimingStep4Fragment())
        binding.viewpager.adapter = adapter
        binding.viewpager.setSwipePagingEnabled(false)
        binding.viewpager.offscreenPageLimit = 2
        binding.indicator.setViewPager(binding.viewpager)

        binding.viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {
                binding.tvTitle.setText(PagerModel.values()[position].titleResId)
            }
        })
    }

//region

// endregion

    fun navController() = findNavController()
}
