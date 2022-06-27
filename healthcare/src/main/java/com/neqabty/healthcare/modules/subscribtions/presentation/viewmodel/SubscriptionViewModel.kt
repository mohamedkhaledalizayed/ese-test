package com.neqabty.healthcare.modules.subscribtions.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.core.utils.AppUtils
import com.neqabty.healthcare.core.utils.Resource
import com.neqabty.healthcare.modules.subscribtions.data.model.Followers
import com.neqabty.healthcare.modules.subscribtions.domain.usecases.AddSubscriptionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.collect

@HiltViewModel
class SubscriptionViewModel @Inject constructor(private val addSubscriptionUseCase: AddSubscriptionUseCase) :
    ViewModel() {

    val providers = MutableLiveData<Resource<Boolean>>()
    fun addSubscription(
        name: String,
        birthDate: String,
        email: String,
        address: String,
        job: String,
        mobile: String,
        nationalId: String,
        syndicateId: Int,
        packageId: String,
        referralNumber: String,
        personalImage: String,
        fronIdImage: String,
        backIdImage: String,
        followers: List<Followers>
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            providers.postValue(Resource.loading(data = null))
            try {

                addSubscriptionUseCase.build(
                    name,
                    birthDate,
                    email,
                    address,
                    job,
                    mobile,
                    nationalId,
                    syndicateId,
                    packageId,
                    referralNumber,
                    personalImage,
                    fronIdImage,
                    backIdImage,
                    followers
                ).collect {
                    providers.postValue(Resource.success(data = it))
                }
            }catch (e:Throwable){
                providers.postValue(Resource.error(data = null, message = AppUtils().handleError(e)))
            }

        }
    }
}