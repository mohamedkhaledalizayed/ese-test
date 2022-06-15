package com.neqabty.healthcare.modules.search.presentation.view.searchresult



import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.core.utils.AppUtils
import com.neqabty.healthcare.core.utils.Resource
import com.neqabty.healthcare.modules.search.domain.entity.MedicalProviderEntity
import com.neqabty.healthcare.modules.search.domain.interactors.GetMedicalProviderstUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val getMedicalProviderstUseCase: GetMedicalProviderstUseCase) :
    ViewModel() {
    val providers = MutableLiveData<Resource<List<MedicalProviderEntity>>>()
    fun getProviders() {
        viewModelScope.launch(Dispatchers.IO) {
            providers.postValue(Resource.loading(data = null))
            try {
                getMedicalProviderstUseCase.build().collect {
                    providers.postValue(Resource.success(data = it))
                }
            }catch (e:Throwable){
                providers.postValue(Resource.error(data = null, message = AppUtils().handleError(e)))
            }
        }
    }
}