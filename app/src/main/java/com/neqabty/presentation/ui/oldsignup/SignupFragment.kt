package com.neqabty.presentation.ui.oldsignup

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
import com.neqabty.databinding.OldSignupFragmentBinding
import com.neqabty.databinding.SignupFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.ui.common.CustomFragmentPagerAdapter
import com.neqabty.presentation.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupFragment : BaseFragment() {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<OldSignupFragmentBinding>()

    private val signupViewModel: SignupViewModel by viewModels()

//    private lateinit var viewPager: ViewPager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.old_signup_fragment,
                container,
                false,
                dataBindingComponent
        )

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        initializeObservers()
        initializeViews()
//        signupViewModel.signup("m@m.m", "Mona", "Mohamed", "01119850766", "1", "1", "1", "123@pass")
    }
    private fun handleViewState(state: SignupViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        state.user?.let {
        }
    }

    fun initializeViews() {
        val adapter = CustomFragmentPagerAdapter(requireFragmentManager())
        adapter.addFragment(SignupStep1Fragment())
        adapter.addFragment(SignupStep2Fragment())
        adapter.addFragment(SignupStep3Fragment())
        binding.viewpager.adapter = adapter
        binding.viewpager.setSwipePagingEnabled(true) // TODO
        binding.indicator.setViewPager(binding.viewpager)

        binding.viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {
                binding.tvTitle.setText(Model.values()[position].titleResId)
            }
        })
    }

//region

// endregion

    fun navController() = findNavController()
}
