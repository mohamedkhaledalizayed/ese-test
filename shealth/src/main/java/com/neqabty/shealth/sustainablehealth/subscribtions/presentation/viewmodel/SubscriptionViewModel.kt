package com.neqabty.shealth.sustainablehealth.subscribtions.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.shealth.core.utils.AppUtils
import com.neqabty.shealth.core.utils.Resource
import com.neqabty.shealth.sustainablehealth.subscribtions.data.model.SubscribePostBodyRequest
import com.neqabty.shealth.sustainablehealth.subscribtions.domain.entity.relations.RelationEntity
import com.neqabty.shealth.sustainablehealth.subscribtions.domain.entity.subscribtions.SubscriptionEntity
import com.neqabty.shealth.sustainablehealth.subscribtions.domain.usecases.AddSubscriptionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubscriptionViewModel @Inject constructor(private val addSubscriptionUseCase: AddSubscriptionUseCase) :
    ViewModel() {

    val relations = MutableLiveData<Resource<List<RelationEntity>>>()
    fun getRelations(){
        viewModelScope.launch(Dispatchers.IO){
            relations.postValue(Resource.loading(data = null))
            try {
                addSubscriptionUseCase.build().collect {
                    relations.postValue(Resource.success(data = it))
                }
            }catch (e: Throwable){
                relations.postValue(Resource.error(data = null, message = AppUtils().handleError(e)))
            }
        }
    }

    val providers = MutableLiveData<Resource<SubscriptionEntity>>()
    fun addSubscription(subscribePostBodyRequest: SubscribePostBodyRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            providers.postValue(Resource.loading(data = null))
            try {

                addSubscriptionUseCase.build(
                    subscribePostBodyRequest
                ).collect {
                    providers.postValue(Resource.success(data = it))
                }
            }catch (e:Throwable){
                providers.postValue(Resource.error(data = null, message = AppUtils().handleError(e)))
            }

        }
    }
}