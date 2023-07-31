package com.neqabty.healthcare.core.syndicates

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.core.ui.BaseViewModel
import com.neqabty.healthcare.core.utils.AppUtils
import com.neqabty.healthcare.core.utils.Resource
import com.neqabty.healthcare.syndicates.domain.entity.SyndicateEntity
import com.neqabty.healthcare.syndicates.domain.interactors.GetSyndicateUseCase
import com.neqabty.healthcare.syndicateservices.domain.entity.SyndicateServiceEntity
import com.neqabty.healthcare.syndicateservices.domain.interactors.SyndicatesServicesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SyndicatesViewModel @Inject constructor(
    private val getSyndicateUseCase: GetSyndicateUseCase,
    private val syndicateServicesUseCase: SyndicatesServicesUseCase
) : BaseViewModel() {
    val syndicates = MutableLiveData<Resource<List<SyndicateEntity>>>()
    fun getSyndicates() {
        syndicates.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getSyndicateUseCase.build().collect {
                    syndicates.postValue(Resource.success(data = it))
                }
            } catch (exception: Throwable) {
                syndicates.postValue(
                    Resource.error(
                        data = null,
                        message = AppUtils().handleError(exception)
                    )
                )
            }
        }
    }

    val syndicateServices = MutableLiveData<Resource<List<SyndicateServiceEntity>>>()
    fun getSyndicateServices(entityCode: String, serviceCategory: String) {
        viewModelScope.launch(Dispatchers.IO) {
//            syndicateServices.postValue(Resource.loading(data = null))
            try {
                syndicateServicesUseCase.build(entityCode, serviceCategory).collect {
                    syndicateServices.postValue(Resource.success(data = it))
                }
            } catch (e: Throwable) {
                syndicateServices.postValue(Resource.error(data = null, message = handleError(e)))
            }
        }
    }
}