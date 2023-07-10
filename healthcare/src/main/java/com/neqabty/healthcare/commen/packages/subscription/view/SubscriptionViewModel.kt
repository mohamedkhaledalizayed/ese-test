package com.neqabty.healthcare.commen.packages.subscription.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.commen.packages.subscription.data.model.SubscribePostBodyRequest
import com.neqabty.healthcare.commen.packages.subscription.domain.entity.SubscriptionEntity
import com.neqabty.healthcare.commen.packages.subscription.domain.usecases.AddSubscriptionUseCase
import com.neqabty.healthcare.core.utils.AppUtils
import com.neqabty.healthcare.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubscriptionViewModel @Inject constructor(private val addSubscriptionUseCase: AddSubscriptionUseCase) :
    ViewModel() {

    val subscriptionStatus = MutableLiveData<Resource<SubscriptionEntity>>()
    fun subscription(subscribePostBodyRequest: SubscribePostBodyRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            subscriptionStatus.postValue(Resource.loading(data = null))
            try {

                addSubscriptionUseCase.build(
                    subscribePostBodyRequest
                ).collect {
                    subscriptionStatus.postValue(Resource.success(data = it))
                }
            } catch (e: Throwable) {
                subscriptionStatus.postValue(
                    Resource.error(
                        data = null,
                        message = AppUtils().handleError(e)
                    )
                )
            }
        }
    }

}