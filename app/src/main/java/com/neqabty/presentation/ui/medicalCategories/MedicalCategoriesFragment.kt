package com.neqabty.presentation.ui.medicalCategories

import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.MedicalCategoriesFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.util.autoCleared
import com.neqabty.testing.OpenForTesting
import javax.inject.Inject

@OpenForTesting
class MedicalCategoriesFragment : BaseFragment(), Injectable {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<MedicalCategoriesFragmentBinding>()
    private var adapter by autoCleared<MedicalCategoriesAdapter>()

    @Inject
    lateinit var appExecutors: AppExecutors

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.medical_categories_fragment,
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
        var categoriesList = mutableListOf<MedicalCategoryUI>()
        categoriesList.add(MedicalCategoryUI(1,getString(R.string.hospitals),R.mipmap.hosp_ic))
        categoriesList.add(MedicalCategoryUI(0,getString(R.string.doctors),R.mipmap.dr_ic))
        categoriesList.add(MedicalCategoryUI(0,getString(R.string.pharmacies),R.mipmap.pharma_ic))
        categoriesList.add(MedicalCategoryUI(3,getString(R.string.scan_centers),R.mipmap.scan_ic))
        categoriesList.add(MedicalCategoryUI(2,getString(R.string.laboratories),R.mipmap.lab_ic))
        categoriesList.add(MedicalCategoryUI(0,getString(R.string.medical_services),R.mipmap.med_serv_ic))

        adapter = MedicalCategoriesAdapter(dataBindingComponent, appExecutors) { category ->
                navController().navigate(
                        MedicalCategoriesFragmentDirections.openCategory(category.id)
                )
        }
        adapter.submitList(categoriesList)

        binding.rvCategories.adapter = adapter
    }

//region

// endregion

    fun navController() = findNavController()
}
