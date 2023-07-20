package com.neqabty.healthcare.medicalnetwork.presentation.view.providerdetails



import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.core.utils.AppUtils
import com.neqabty.healthcare.core.utils.Resource
import com.neqabty.healthcare.medicalnetwork.domain.entity.search.ProvidersEntity
import com.neqabty.healthcare.medicalnetwork.domain.interactors.GetMedicalProviderstUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProviderDetailsViewModel @Inject constructor(private val getMedicalProviderstUseCase: GetMedicalProviderstUseCase) :
    ViewModel() {
    val providerDetails = MutableLiveData<Resource<ProvidersEntity>>()
    fun getProviderDetails(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            providerDetails.postValue(Resource.loading(data = null))
            try {
                getMedicalProviderstUseCase.build(id).collect {
                    providerDetails.postValue(Resource.success(data = it))
                }
            }catch (e:Throwable){
                providerDetails.postValue(Resource.error(data = null, message = AppUtils().handleError(e)))
            }
        }
    }
}