package com.neqabty.chefaa.modules.verifyuser.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.chefaa.core.utils.AppUtils
import com.neqabty.chefaa.core.utils.Resource
import com.neqabty.chefaa.modules.verifyuser.domain.interactors.VerificationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerifyUserViewModel @Inject constructor(private val verificationUseCase: VerificationUseCase):
    ViewModel() {

    val status = MutableLiveData<Resource<Boolean>>()
    fun verifyUser(mobile: String, code: String){
        viewModelScope.launch(Dispatchers.IO) {
            status.postValue(Resource.loading(data = null))

            try {
                verificationUseCase.build(mobile, code).collect{
                    status.postValue(Resource.success(data = it))
                }
            }catch (e: Throwable){
                status.postValue(Resource.error(data = null, message = AppUtils().handleError(e)))
            }
        }
    }

}