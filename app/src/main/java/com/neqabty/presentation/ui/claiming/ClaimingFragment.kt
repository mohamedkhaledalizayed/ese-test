package com.neqabty.presentation.ui.claiming

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.neqabty.R
import com.neqabty.databinding.ClaimingFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.ui.common.CustomPagerAdapter
import com.neqabty.presentation.util.autoCleared
import com.neqabty.testing.OpenForTesting
import javax.inject.Inject

@OpenForTesting
class ClaimingFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<ClaimingFragmentBinding>()

    lateinit var claimingViewModel: ClaimingViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
//        setupToolbar(true)
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
        claimingViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(ClaimingViewModel::class.java)
        initializeViews()
    }

    fun initializeViews() {
        val adapter = CustomPagerAdapter(requireFragmentManager())
        adapter.addFragment(com.neqabty.presentation.ui.claiming.ClaimingStep1Fragment())
        adapter.addFragment(com.neqabty.presentation.ui.claiming.ClaimingStep2Fragment())
        adapter.addFragment(com.neqabty.presentation.ui.claiming.ClaimingStep3Fragment())
        adapter.addFragment(com.neqabty.presentation.ui.claiming.ClaimingStep4Fragment())
        binding.viewpager.adapter = adapter
        binding.viewpager.setSwipePagingEnabled(true)//TODO
        binding.indicator.setViewPager(binding.viewpager)


//        binding.viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
//            override fun onPageScrollStateChanged(state: Int) {}
//            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
//            override fun onPageSelected(position: Int) {
//                binding.tvTitle.setText(Model.values()[position].titleResId)
//            }
//        })
    }


//region


// endregion

    fun navController() = findNavController()
}
