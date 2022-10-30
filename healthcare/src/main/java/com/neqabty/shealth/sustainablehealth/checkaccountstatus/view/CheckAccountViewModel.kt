package com.neqabty.shealth.sustainablehealth.checkaccountstatus.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.shealth.core.utils.Resource
import com.neqabty.shealth.sustainablehealth.checkaccountstatus.data.model.CheckPhoneBody
import com.neqabty.shealth.sustainablehealth.checkaccountstatus.domain.usecases.CheckAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class CheckAccountViewModel @Inject constructor(private val checkAccountUseCase: CheckAccountUseCase): ViewModel() {

    val accountStatus = MutableLiveData<Resource<String>>()
    fun checkAccount(checkPhoneBody: CheckPhoneBody){
        viewModelScope.launch(Dispatchers.IO){
            accountStatus.postValue(Resource.loading(data = null))

            try {
                checkAccountUseCase.build(checkPhoneBody).collect(){
                    accountStatus.postValue(Resource.success(data = it))
                }
            }catch (e: Throwable){
                accountStatus.postValue(Resource.error(data = null, message = handleError(e)))
            }
        }
    }

    fun handleError(throwable: Throwable): String {
        return if (throwable is HttpException) {
            when (throwable.code()) {
                400 -> {
                    "لقد تم تسجيل الدخول من قبل برجاء تسجيل الخروج واعادة المحاولة مرة اخرى"
                }
                401 -> {
                    "لقد تم تسجيل الدخول من قبل برجاء تسجيل الخروج واعادة المحاولة مرة اخرى"
                }
                403 -> {
                    "لقد تم تسجيل الدخول من قبل برجاء تسجيل الخروج واعادة المحاولة مرة اخرى"
                }
                404 -> {
                    "404"
                }
                500 -> {
                    "نأسف، لقد حدث خطأ.. برجاء المحاولة في وقت لاحق"
                }
                else -> {
                    throwable.message!!
                }
            }
        } else {
            throwable.message!!
        }
    }

}