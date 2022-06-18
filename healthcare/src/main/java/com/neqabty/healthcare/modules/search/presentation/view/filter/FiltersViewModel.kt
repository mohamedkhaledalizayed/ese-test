package com.neqabty.healthcare.modules.search.presentation.view.filter



import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.core.utils.AppUtils
import com.neqabty.healthcare.core.utils.Resource
import com.neqabty.healthcare.modules.search.domain.interactors.GetMedicalProviderstUseCase
import com.neqabty.healthcare.modules.search.presentation.model.filters.FiltersUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
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
}