package com.neqabty.meganeqabty.payment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.meganeqabty.core.utils.AppUtils
import com.neqabty.meganeqabty.core.utils.Resource
import com.neqabty.meganeqabty.syndicates.domain.entity.SyndicateEntity
import com.neqabty.meganeqabty.syndicates.domain.interactors.GetSyndicateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(private val getSyndicateUseCase: GetSyndicateUseCase) :
    ViewModel() {
    val syndicates = MutableLiveData<Resource<List<SyndicateEntity>>>()
    fun getSyndicates() {
        syndicates.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getSyndicateUseCase.build().collect {
                    syndicates.postValue(Resource.success(data = it))
                }
            }catch (exception:Throwable){
                syndicates.postValue(Resource.error(data = null, message = AppUtils().handleError(exception)))
            }
        }
    }
}