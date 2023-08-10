package com.neqabty.healthcare.retirement.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.core.utils.Resource
import com.neqabty.healthcare.retirement.data.model.inheritor.InheritorModel
import com.neqabty.healthcare.retirement.data.model.pension.PensionModel
import com.neqabty.healthcare.retirement.data.model.validation.ValidationModel
import com.neqabty.healthcare.retirement.domain.usecases.CheckValidationUseCase
import com.neqabty.healthcare.retirement.domain.usecases.GetInheritorUseCase
import com.neqabty.healthcare.retirement.domain.usecases.GetPensionInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class RetirementViewModel @Inject constructor(
    private val checkValidationUseCase: CheckValidationUseCase,
    private val getPensionInfoUseCase: GetPensionInfoUseCase,
    private val getInheritorUseCase: GetInheritorUseCase
) :
    ViewModel() {

    val userStatus = MutableLiveData<Resource<ValidationModel>>()
    fun checkValidation(userNumber: String, nationalId: String) {
        userStatus.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                checkValidationUseCase.build(userNumber, nationalId).collect {
                    if (it.isSuccessful){
                        userStatus.postValue(Resource.success(data = it.body()!!))
                    }else{
                        val jObjError = JSONObject(it.errorBody()!!.string()).toString()
                        userStatus.postValue(Resource.error(data = null, message = "3ak # $jObjError"))
                    }
                }
            }catch (exception:Throwable){
                userStatus.postValue(Resource.error(data = null, message = handleError(exception)))
            }
        }
    }

    val pensionInfo = MutableLiveData<Resource<PensionModel>>()
    fun getPensionInfo(id: String) {
        pensionInfo.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getPensionInfoUseCase.build(id).collect {
                    if (it.isSuccessful){
                        pensionInfo.postValue(Resource.success(data = it.body()!!))
                    }else{
                        val jObjError = JSONObject(it.errorBody()!!.string()).toString()
                        pensionInfo.postValue(Resource.error(data = null, message = "3ak # $jObjError"))
                    }
                }
            }catch (exception: Throwable){
                pensionInfo.postValue(Resource.error(data = null, message = handleError(exception)))
            }
        }
    }

    val inheritorInfo = MutableLiveData<Resource<InheritorModel>>()
    fun getInheritor(id: String) {
        pensionInfo.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getInheritorUseCase.build(id).collect {
                    if (it.isSuccessful){
                        inheritorInfo.postValue(Resource.success(data = it.body()!!))
                    }else{
                        val jObjError = JSONObject(it.errorBody()!!.string()).toString()
                        inheritorInfo.postValue(Resource.error(data = null, message = "3ak # $jObjError"))
                    }
                }
            }catch (exception: Throwable){
                inheritorInfo.postValue(Resource.error(data = null, message = handleError(exception)))
            }
        }
    }

    private fun handleError(throwable: Throwable): String {
        return if (throwable is HttpException) {
            when (throwable.code()) {
                400 -> {
                    "400"
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
                409 -> {
                    "409"
                }
                500 -> {
                    "نأسف، لقد حدث خطأ.. برجاء المحاولة في وقت لاحق"
                }
                else -> {
                    "نأسف، لقد حدث خطأ.. برجاء المحاولة في وقت لاحق"
                }
            }
        } else {
            "نأسف، لقد حدث خطأ.. برجاء المحاولة في وقت لاحق"
        }
    }
}