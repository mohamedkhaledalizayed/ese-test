package com.neqabty.presentation.ui.medicalCategories

import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
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
import com.neqabty.presentation.util.HasMedicalOptionsMenu
import com.neqabty.presentation.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MedicalCategoriesFragment : BaseFragment(), HasMedicalOptionsMenu {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<MedicalCategoriesFragmentBinding>()
    private var adapter by autoCleared<MedicalCategoriesAdapter>()

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
                R.layout.medical_categories_fragment,
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
        var categoriesList = mutableListOf<MedicalCategoryUI>()
        categoriesList.add(MedicalCategoryUI(1, getString(R.string.hospitals), R.mipmap.hosp_ic))
        categoriesList.add(MedicalCategoryUI(2, getString(R.string.doctors), R.mipmap.dr_ic))
        categoriesList.add(MedicalCategoryUI(16, getString(R.string.pharmacies), R.mipmap.pharma_ic))
        categoriesList.add(MedicalCategoryUI(3, getString(R.string.scan_centers), R.mipmap.scan_ic))
        categoriesList.add(MedicalCategoryUI(4, getString(R.string.laboratories), R.mipmap.lab_ic))
        categoriesList.add(MedicalCategoryUI(18, getString(R.string.others), R.mipmap.med_serv_ic)) // 2027 2028
        categoriesList.add(MedicalCategoryUI(2028, getString(R.string.optics), R.mipmap.optics)) // 2028
        categoriesList.add(MedicalCategoryUI(2027, getString(R.string.artificial_limbs), R.mipmap.artificial_limbs_ic)) // 2027 2028
        categoriesList.add(MedicalCategoryUI(17, getString(R.string.pathology_labs), R.mipmap.lab_ic))

        adapter = MedicalCategoriesAdapter(dataBindingComponent, appExecutors) { category ->
            when (category.id) {
                2 ->
                    navController().navigate(
                            MedicalCategoriesFragmentDirections.openProfessions(category.name, category.id, governID, areaID)
                    )
                else ->
                    navController().navigate(
                            MedicalCategoriesFragmentDirections.openProviders(category.name, category.id, governID, areaID, "","", "")
                    )
            }
        }
        adapter.submitList(categoriesList)

        binding.rvCategories.adapter = adapter
    }

    override fun showOptionsMenu() {
    }

//region

// endregion

    fun navController() = findNavController()
}
