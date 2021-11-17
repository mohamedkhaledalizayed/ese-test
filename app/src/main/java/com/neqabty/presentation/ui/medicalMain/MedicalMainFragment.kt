package com.neqabty.presentation.ui.medicalMain

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TableLayout
import androidx.constraintlayout.widget.ConstraintLayout
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
import com.neqabty.presentation.util.DisplayMetrics
import com.neqabty.presentation.util.HasMedicalOptionsMenu
import com.neqabty.presentation.util.autoCleared
import kotlinx.android.synthetic.main.medical_main_fragment.*
import javax.inject.Inject


class MedicalMainFragment : BaseFragment(), HasMedicalOptionsMenu, Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<MedicalMainFragmentBinding>()

    @Inject
    lateinit var appExecutors: AppExecutors
    var areaID: Int = 0
    var governID: Int = 0
    var name: String = ""
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

        val params = MedicalMainFragmentArgs.fromBundle(arguments!!)
        name = params.name
        areaID = params.areaID
        governID = params.governID

        initializeViews()
    }

    fun initializeViews() {

        val categoriesNameList = mutableListOf<String>(getString(R.string.pathology_labs), getString(R.string.artificial_limbs), getString(R.string.optics), getString(R.string.others), getString(R.string.laboratories),
                getString(R.string.scan_centers), getString(R.string.pharmacies), getString(R.string.doctors)
                , getString(R.string.hospitals))
        val categoriesIDList = mutableListOf<Int>(17, 2027, 2028, 18, 4, 3, 16, 2, 1)

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(categoriesNameList[0]))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(categoriesNameList[1]))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(categoriesNameList[2]))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(categoriesNameList[3]))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(categoriesNameList[4]))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(categoriesNameList[5]))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(categoriesNameList[6]))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(categoriesNameList[7]))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(categoriesNameList[8]))

        val headerView: View = LayoutInflater.from(context).inflate(R.layout.tab_medical_item, null, false)

        binding.tabLayout.getTabAt(0)?.setCustomView(headerView.findViewById<ConstraintLayout>(R.id.clPathologyLabs))
        binding.tabLayout.getTabAt(1)?.setCustomView(headerView.findViewById<ConstraintLayout>(R.id.clArtificialLimbs))
        binding.tabLayout.getTabAt(2)?.setCustomView(headerView.findViewById<ConstraintLayout>(R.id.clOptics))
        binding.tabLayout.getTabAt(3)?.setCustomView(headerView.findViewById<ConstraintLayout>(R.id.clOthers))
        binding.tabLayout.getTabAt(4)?.setCustomView(headerView.findViewById<ConstraintLayout>(R.id.clLabs))
        binding.tabLayout.getTabAt(5)?.setCustomView(headerView.findViewById<ConstraintLayout>(R.id.clScans))
        binding.tabLayout.getTabAt(6)?.setCustomView(headerView.findViewById<ConstraintLayout>(R.id.clPharmacies))
        binding.tabLayout.getTabAt(7)?.setCustomView(headerView.findViewById<ConstraintLayout>(R.id.clDoctors))
        binding.tabLayout.getTabAt(8)?.setCustomView(headerView.findViewById<ConstraintLayout>(R.id.clHospitals))

        tabLayout.requestLayout()

//        val itemLayoutParams = headerView.layoutParams

        val layoutParams: ViewGroup.LayoutParams = tabLayout.layoutParams
        layoutParams.height = DisplayMetrics.width * 35 / 100
        tabLayout.layoutParams = layoutParams

//        binding.tabLayout.getTabAt(0)?.view?.tab!!.customView?.layoutParams = LinearLayout.LayoutParams(DisplayMetrics.width / 3,DisplayMetrics.width / 3 - 20)
//        binding.tabLayout.getTabAt(0)?.customView?.setPadding(10, 10, 10, 10);
//        binding.tabLayout.layoutParams = LinearLayout.LayoutParams(DisplayMetrics.width / 3, DisplayMetrics.width / 3)

        viewpager.adapter = CategoriesTabAdapter(requireContext(), childFragmentManager, tabLayout!!.tabCount, categoriesIDList, categoriesNameList, name, areaID, governID)


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

        val tab = binding.tabLayout.getTabAt(8)
        tab?.select()
        viewpager.currentItem = 8


//        val adapter = CustomFragmentPagerAdapter(childFragmentManager)
//        adapter.addFragment(WheelNewsFragment())
//        adapter.addFragment(WheelTripsFragment())
//        adapter.addFragment(WheelPaymentsFragment())
//        adapter.addFragment(ClaimingStep4Fragment())
//        adapter.addFragment(WheelMedicalFragment())
//        binding.viewpager.adapter = adapter
//        binding.viewpager.setSwipePagingEnabled(false)
        binding.viewpager.offscreenPageLimit = 0
    }

    override fun showOptionsMenu() {
        TODO("Not yet implemented")
    }
    //region
// endregion

    fun navController() = findNavController()
}
