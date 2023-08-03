package com.neqabty.healthcare.auth.forgetpassword.view.changepassword

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.auth.forgetpassword.data.model.ChangePasswordBody
import com.neqabty.healthcare.auth.forgetpassword.domain.entity.ChangePasswordEntity
import com.neqabty.healthcare.auth.forgetpassword.domain.usecases.ChangePassword
import com.neqabty.healthcare.core.ui.BaseViewModel
import com.neqabty.healthcare.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(private val changePassword: ChangePassword): BaseViewModel() {

    val status = MutableLiveData<Resource<ChangePasswordEntity>>()
    fun changePassword(body: ChangePasswordBody, token: String){
        viewModelScope.launch(Dispatchers.IO){
            status.postValue(Resource.loading(data = null))
            try {
                changePassword.build(body, token).collect {
                    status.postValue(Resource.success(data = it))
                }
            }catch (e: Throwable){
                status.postValue(Resource.error(data = null, message = handleError(e)))
            }
        }
    }

}