package com.neqabty.presentation.ui.claiming

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.neqabty.R
import com.neqabty.databinding.Claiming3FragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.util.autoCleared
import com.neqabty.testing.OpenForTesting
import javax.inject.Inject

@OpenForTesting
class ClaimingStep3Fragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<Claiming3FragmentBinding>()

    lateinit var claimingViewModel: ClaimingViewModel

    lateinit var pager: ViewPager
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.claiming3_fragment,
                container,
                false,
                dataBindingComponent
        )
        pager = container as ViewPager
        return binding.root
    }


    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser)
            initializeViews()
    }

    fun initializeViews() {
        claimingViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(ClaimingViewModel::class.java)


        binding.edNumber.setText(ClaimingData.number)
        binding.edDoctor.setText(ClaimingData.doctorName)
        binding.edProvider.setText(ClaimingData.providerName)

        binding.bNext.setOnClickListener({
            pager.setCurrentItem(3,true)
        })

    }

//region


// endregion

}
