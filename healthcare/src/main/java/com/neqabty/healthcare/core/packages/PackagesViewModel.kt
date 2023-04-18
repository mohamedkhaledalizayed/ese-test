package com.neqabty.healthcare.core.packages

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.commen.syndicates.domain.entity.SyndicateEntity
import com.neqabty.healthcare.commen.syndicates.domain.interactors.GetSyndicateUseCase
import com.neqabty.healthcare.core.ui.BaseViewModel
import com.neqabty.healthcare.core.utils.AppUtils
import com.neqabty.healthcare.core.utils.Resource
import com.neqabty.healthcare.sustainablehealth.search.domain.entity.packages.PackagesEntity
import com.neqabty.healthcare.sustainablehealth.search.domain.interactors.GetMedicalProviderstUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PackagesViewModel @Inject constructor(
    private val getMedicalProvidersUseCase: GetMedicalProviderstUseCase
) : BaseViewModel() {
    val packages = MutableLiveData<Resource<List<PackagesEntity>>>()
    fun getPackages(code: String) {
        viewModelScope.launch(Dispatchers.IO) {
            packages.postValue(Resource.loading(data = null))
            try {
                getMedicalProvidersUseCase.getPackages(code).collect {
                    packages.postValue(Resource.success(data = it))
                }
            }catch (e:Throwable){
                packages.postValue(Resource.error(data = null, message = AppUtils().handleError(e)))
            }
        }
    }
}