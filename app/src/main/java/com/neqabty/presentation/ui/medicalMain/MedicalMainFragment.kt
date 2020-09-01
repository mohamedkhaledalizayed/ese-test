package com.neqabty.presentation.ui.medicalMain

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.MedicalMainFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.ui.medicalCategories.MedicalCategoriesFragmentArgs
import com.neqabty.presentation.util.autoCleared
import kotlinx.android.synthetic.main.medical_main_fragment.*
import kotlinx.android.synthetic.main.medical_main_fragment.tabLayout
import kotlinx.android.synthetic.main.notifications_fragment_old.*
import javax.inject.Inject

class MedicalMainFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<MedicalMainFragmentBinding>()

    @Inject
    lateinit var appExecutors: AppExecutors
    var areaID: Int = 0
    var governID: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.medical_main_fragment,
                container,
                false,
                dataBindingComponent
        )

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val params = MedicalCategoriesFragmentArgs.fromBundle(arguments!!)
        areaID = params.areaID
        governID = params.governID

        initializeViews()
    }

    fun initializeViews() {
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(getString(R.string.trips)))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(getString(R.string.claiming_title)))
        binding.tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = CategoriesTabAdapter(requireContext(), childFragmentManager, tabLayout!!.tabCount)
        viewpager.adapter = adapter

        viewpager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewpager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })

        val tab = binding.tabLayout.getTabAt(0)
        tab?.select()
        viewpager.currentItem = 1


//        val adapter = CustomFragmentPagerAdapter(childFragmentManager)
//        adapter.addFragment(WheelNewsFragment())
//        adapter.addFragment(WheelTripsFragment())
//        adapter.addFragment(WheelPaymentsFragment())
//        adapter.addFragment(ClaimingStep4Fragment())
//        adapter.addFragment(WheelMedicalFragment())
//        binding.viewpager.adapter = adapter
//        binding.viewpager.setSwipePagingEnabled(false)
//        binding.viewpager.offscreenPageLimit = 2
    }

    //region
// endregion

    fun navController() = findNavController()
}
