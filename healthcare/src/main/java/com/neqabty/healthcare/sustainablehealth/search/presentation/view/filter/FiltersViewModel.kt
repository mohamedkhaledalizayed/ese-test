package com.neqabty.healthcare.sustainablehealth.search.presentation.view.filter



import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.core.utils.AppUtils
import com.neqabty.healthcare.core.utils.Resource
import com.neqabty.healthcare.sustainablehealth.home.domain.entity.about.packages.PackagesEntity
import com.neqabty.healthcare.sustainablehealth.search.domain.interactors.GetMedicalProviderstUseCase
import com.neqabty.healthcare.sustainablehealth.search.presentation.mappers.toAreaListUi
import com.neqabty.healthcare.sustainablehealth.search.presentation.model.filters.FiltersUi
import com.neqabty.healthcare.sustainablehealth.search.presentation.model.filters.ItemUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FiltersViewModel @Inject constructor(private val getMedicalProviderstUseCase: GetMedicalProviderstUseCase) :
    ViewModel() {

    val filters = MutableLiveData<Resource<FiltersUi>>()
    fun getFilters() {
        viewModelScope.launch(Dispatchers.IO) {
            filters.postValue(Resource.loading(data = null))
            try {
                getMedicalProviderstUseCase.getFilters().collect {
                    filters.postValue(Resource.success(data = it))
                }
            }catch (e:Throwable){
                filters.postValue(Resource.error(data = null, message = AppUtils().handleError(e)))
            }
        }
    }

    val area = MutableLiveData<Resource<List<ItemUi>>>()
    fun getAreas(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            area.postValue(Resource.loading(data = null))
            try {
                getMedicalProviderstUseCase.getArea(id).collect {
                    area.postValue(Resource.success(data = it.map { it.toAreaListUi() }))
                }
            }catch (e:Throwable){
                area.postValue(Resource.error(data = null, message = AppUtils().handleError(e)))
            }
        }
    }

}