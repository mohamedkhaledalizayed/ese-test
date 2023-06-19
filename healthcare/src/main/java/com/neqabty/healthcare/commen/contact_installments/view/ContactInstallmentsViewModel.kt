package com.neqabty.healthcare.commen.contact_installments.view



import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.commen.onboarding.contact.domain.entity.InstallmentsEntity
import com.neqabty.healthcare.commen.onboarding.contact.domain.interactors.GetInstallmentsUseCase
import com.neqabty.healthcare.core.utils.AppUtils
import com.neqabty.healthcare.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactInstallmentsViewModel @Inject constructor(private val getInstallmentsUseCase: GetInstallmentsUseCase) :
    ViewModel() {
    val installments = MutableLiveData<Resource<InstallmentsEntity>>()
    fun getInstallments(nationalId: String, amount: String, tenor: String) {
        viewModelScope.launch(Dispatchers.IO) {
            installments.postValue(Resource.loading(data = null))
            try {
                getInstallmentsUseCase.build(nationalId, amount, tenor).collect {
                    installments.postValue(Resource.success(data = it))
                }
            }catch (e:Throwable){
                installments.postValue(Resource.error(data = null, message = AppUtils().handleError(e)))
            }
        }
    }
}