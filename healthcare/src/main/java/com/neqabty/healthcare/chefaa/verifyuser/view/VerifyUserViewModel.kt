package com.neqabty.healthcare.chefaa.verifyuser.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.chefaa.verifyuser.domain.interactors.VerificationUseCase
import com.neqabty.healthcare.core.utils.AppUtils
import com.neqabty.healthcare.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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