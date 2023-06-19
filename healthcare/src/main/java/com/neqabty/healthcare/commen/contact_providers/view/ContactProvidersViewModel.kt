package com.neqabty.healthcare.commen.contact_providers.view



import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.core.utils.AppUtils
import com.neqabty.healthcare.core.utils.Resource
import com.neqabty.healthcare.sustainablehealth.medicalnetwork.data.model.SearchBody
import com.neqabty.healthcare.sustainablehealth.medicalnetwork.domain.entity.search.ProvidersEntity
import com.neqabty.healthcare.sustainablehealth.medicalnetwork.domain.interactors.GetMedicalProviderstUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactProvidersViewModel @Inject constructor(private val getMedicalProviderstUseCase: GetMedicalProviderstUseCase) :
    ViewModel() {
    val providers = MutableLiveData<Resource<List<ProvidersEntity>>>()
    fun getProviders(body: SearchBody) {
        viewModelScope.launch(Dispatchers.IO) {
            providers.postValue(Resource.loading(data = null))
            try {
                getMedicalProviderstUseCase.build(body).collect {
                    providers.postValue(Resource.success(data = it))
                }
            }catch (e:Throwable){
                providers.postValue(Resource.error(data = null, message = AppUtils().handleError(e)))
            }
        }
    }
}