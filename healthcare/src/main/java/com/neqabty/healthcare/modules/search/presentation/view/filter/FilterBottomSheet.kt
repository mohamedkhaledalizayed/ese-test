package com.neqabty.healthcare.modules.search.presentation.view.filter


import android.annotation.SuppressLint
import android.app.Dialog
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.FragmentFilterBottomSheetBinding
import com.neqabty.healthcare.modules.search.presentation.model.filters.FiltersUi
import com.neqabty.healthcare.modules.search.presentation.model.filters.ItemUi
import com.neqabty.healthcare.modules.search.presentation.view.searchresult.SearchResultActivity
import com.neqabty.healthcare.modules.search.presentation.view.searchresult.selectedGovernorate
import com.neqabty.healthcare.modules.search.presentation.view.searchresult.selectedProfession
import com.neqabty.healthcare.modules.search.presentation.view.searchresult.selectedProviders
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FilterBottomSheet : RoundedBottomSheetDialogFragment() {

    private val filtersViewModel: FiltersViewModel by viewModels()
    private val governorateAdapter = CustomAdapter()
    private val professionAdapter = CustomAdapter()
    private val providerTypesAdapter = CustomAdapter()

    private var governorate: ItemUi? = null
    private var serviceProviderType: ItemUi? = null
    private var profession: ItemUi? = null

    private var filtersData: FiltersUi? = null

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        val bottomSheetDialog = dialog as BottomSheetDialog

        val myDrawerView = layoutInflater.inflate(R.layout.fragment_filter_bottom_sheet, null)
        val binding = FragmentFilterBottomSheetBinding.inflate(
            layoutInflater,
            myDrawerView as ViewGroup,
            false
        )
        bottomSheetDialog.setContentView(binding.mainContainer)




        binding.spGovernment.adapter = governorateAdapter
        binding.spGovernment.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                if (filtersData != null && i != 0) {
                    governorate = filtersData?.governorates?.get(i - 1)
                    selectedGovernorate = i
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }

        binding.spSpe.adapter = professionAdapter
        binding.spSpe.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                if (filtersData != null && i != 0) {
                    profession = filtersData?.professions?.get(i - 1)
                    selectedProfession = i
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }

        binding.spType.adapter = providerTypesAdapter
        binding.spType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                if (filtersData != null && i != 0) {
                    serviceProviderType = filtersData?.providerTypes?.get(i - 1)
                    selectedProviders = i
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }
        filtersViewModel.getFilters()
        filtersViewModel.filters.observe(this) {
            it.let { resource ->

                when (resource.status) {
                    Status.LOADING -> {
                        dialog.findViewById<ProgressBar>(R.id.progress_circular)?.visibility =
                            View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        filtersData = resource.data
                        dialog.findViewById<ProgressBar>(R.id.progress_circular)?.visibility =
                            View.GONE
                        dialog.findViewById<LinearLayout>(R.id.filter_container)?.visibility =
                            View.VISIBLE
                        governorateAdapter.submitList(
                            resource.data?.governorates!!.toMutableList()
                                .also { list -> list.add(0, ItemUi(0, "اختر المحافظة")) })
                        professionAdapter.submitList(
                            resource.data.professions.toMutableList()
                                .also { list -> list.add(0, ItemUi(0, "اختر التخصص")) })
                        providerTypesAdapter.submitList(
                            resource.data.providerTypes.toMutableList()
                                .also { list -> list.add(0, ItemUi(0, "اختر نوع مقدم الخدمة")) })
                        binding.spGovernment.setSelection(selectedGovernorate)
                        binding.spSpe.setSelection(selectedProfession)
                        binding.spType.setSelection(selectedProviders)
                    }
                    Status.ERROR -> {
                        dialog.findViewById<ProgressBar>(R.id.progress_circular)?.visibility =
                            View.GONE
                    }
                }

            }
        }

        binding.filterBtn.setOnClickListener {
            val searchActivity = activity as SearchResultActivity
            searchActivity.onFilterClicked(governorate, profession, serviceProviderType)
            dismiss()
        }

    }
}

