package com.neqabty.presentation.ui.signup

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.neqabty.R
import com.neqabty.databinding.SignupFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.ui.common.CustomFragmentPagerAdapter
import com.neqabty.presentation.util.autoCleared

import javax.inject.Inject


class SignupFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<SignupFragmentBinding>()

    lateinit var signupViewModel: SignupViewModel
//    private lateinit var viewPager: ViewPager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.signup_fragment,
                container,
                false,
                dataBindingComponent
        )

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        signupViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(SignupViewModel::class.java)

//        initializeObservers()
        initializeViews()
//        signupViewModel.signup("m@m.m", "Mona", "Mohamed", "01119850766", "1", "1", "1", "123@pass")
    }
    private fun handleViewState(state: SignupViewState) {
        binding.progressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
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
