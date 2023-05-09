package com.neqabty.healthcare.sustainablehealth.medicalnetwork.presentation.view.filter


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
import com.neqabty.healthcare.sustainablehealth.medicalnetwork.presentation.model.filters.FiltersUi
import com.neqabty.healthcare.sustainablehealth.medicalnetwork.presentation.model.filters.ItemUi
import com.neqabty.healthcare.sustainablehealth.medicalnetwork.presentation.view.searchresult.*
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FilterBottomSheet : RoundedBottomSheetDialogFragment() {

    private val filtersViewModel: FiltersViewModel by viewModels()
    private val governorateAdapter = CustomAdapter()
    private val professionAdapter = CustomAdapter()
    private val providerTypesAdapter = CustomAdapter()
    private val degreeAdapter = CustomAdapter()
    private val areaAdapter = CustomAdapter()

    private var governorate: ItemUi? = null
    private var serviceProviderType: ItemUi? = null
    private var profession: ItemUi? = null
    private var degree: ItemUi? = null
    private var area: ItemUi? = null

    private var filtersData: FiltersUi? = null
    private var areasData: List<ItemUi>? = null

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

                    filtersViewModel.getAreas(governorate!!.id)
                }else{
                    governorate = null
                    binding.area.visibility = View.GONE
                    binding.spinnerAreaContainer.visibility = View.GONE
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

                    if (serviceProviderType?.id == 2){
                        binding.spinnerDegreeContainer.visibility = View.VISIBLE
                        binding.spinnerSpeContainer.visibility = View.VISIBLE
                        binding.degree.visibility = View.VISIBLE
                        binding.professions.visibility = View.VISIBLE
                    }else{
                        binding.spinnerDegreeContainer.visibility = View.GONE
                        binding.spinnerSpeContainer.visibility = View.GONE
                        binding.degree.visibility = View.GONE
                        binding.professions.visibility = View.GONE

                        profession = null
                        degree = null
                    }
                }else{
                    binding.spinnerDegreeContainer.visibility = View.GONE
                    binding.spinnerSpeContainer.visibility = View.GONE
                    binding.degree.visibility = View.GONE
                    binding.professions.visibility = View.GONE

                    serviceProviderType = null
                    profession = null
                    degree = null
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }

        binding.spDegree.adapter = degreeAdapter
        binding.spDegree.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                if (filtersData != null && i != 0) {
                    degree = filtersData?.degrees?.get(i - 1)
                    selectedDegree = i
                }else{
                    degree = null
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }

        binding.spArea.adapter = areaAdapter
        binding.spArea.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                if (areasData != null && i != 0) {
                    area = areasData?.get(i - 1)
                    selectedArea = i
                }else{
                    area = null
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }


        filtersViewModel.getFilters()
        filtersViewModel.filters.observe(this) {
            it.let { resource ->

                when (resource.status) {
                    Status.LOADING -> {
                        binding.progressCircular.visibility =
                            View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        filtersData = resource.data
                        binding.progressCircular.visibility =
                            View.GONE
                        binding.filterContainer.visibility =
                            View.VISIBLE
                        governorateAdapter.submitList(
                            resource.data?.governorates!!.toMutableList()
                                .also { list -> list.add(0, ItemUi(0, "اختر المحافظة")) })
                        professionAdapter.submitList(
                            resource.data!!.professions.toMutableList()
                                .also { list -> list.add(0, ItemUi(0, "اختر التخصص")) })
                        providerTypesAdapter.submitList(
                            resource.data!!.providerTypes.toMutableList()
                                .also { list -> list.add(0, ItemUi(0, "اختر نوع مقدم الخدمة")) })
                        degreeAdapter.submitList(
                            resource.data!!.degrees.toMutableList()
                                .also { list -> list.add(0, ItemUi(0, "اختر الدرجة العلمية")) })
                        binding.spGovernment.setSelection(selectedGovernorate)
                        binding.spSpe.setSelection(selectedProfession)
                        binding.spType.setSelection(selectedProviders)
                        binding.spDegree.setSelection(selectedDegree)
                    }
                    Status.ERROR -> {
                        binding.progressCircular.visibility =
                            View.GONE
                    }
                }

            }
        }


        filtersViewModel.area.observe(this) {
            it.let { resource ->

                when (resource.status) {
                    Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        areasData = resource.data
                        binding.progressCircular.visibility =
                            View.GONE

                        areaAdapter.submitList(
                            resource.data!!.toMutableList()
                                .also { list -> list.add(0, ItemUi(0, "اختر المدينة")) })

                        binding.area.visibility = View.VISIBLE
                        binding.spinnerAreaContainer.visibility = View.VISIBLE
                        binding.spArea.setSelection(selectedArea)
                    }
                    Status.ERROR -> {
                        binding.progressCircular.visibility = View.GONE
                    }
                }

            }
        }

        binding.filterBtn.setOnClickListener {
            val searchActivity = activity as SearchResultActivity
            searchActivity.onFilterClicked(governorate, profession, serviceProviderType, degree, area)
            dismiss()
        }

    }
}
