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

        val categoriesNameList = mutableListOf<String>(getString(R.string.artificial_limbs), getString(R.string.optics), getString(R.string.others), getString(R.string.laboratories),
                getString(R.string.scan_centers), getString(R.string.pharmacies), getString(R.string.doctors)
                , getString(R.string.hospitals))
        val categoriesIDList = mutableListOf<Int>(2027, 2028, 18, 4, 3, 16, 2, 1)

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(categoriesNameList[0]))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(categoriesNameList[1]))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(categoriesNameList[2]))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(categoriesNameList[3]))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(categoriesNameList[4]))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(categoriesNameList[5]))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(categoriesNameList[6]))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(categoriesNameList[7]))
        binding.tabLayout.tabGravity = TabLayout.GRAVITY_CENTER

        viewpager.adapter = CategoriesTabAdapter(requireContext(), childFragmentManager, tabLayout!!.tabCount, categoriesIDList, categoriesNameList, areaID, governID)


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

        val tab = binding.tabLayout.getTabAt(7)
        tab?.select()
        viewpager.currentItem = 7


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
