package com.neqabty.healthcare.commen.packages.payment.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.core.utils.Resource
import com.neqabty.healthcare.commen.packages.payment.data.model.SehaPaymentBody
import com.neqabty.healthcare.commen.packages.payment.data.model.sehapayment.SehaPaymentResponse
import com.neqabty.healthcare.commen.packages.payment.domain.entity.paymentmethods.PaymentEntity
import com.neqabty.healthcare.commen.packages.payment.domain.usecase.PaymentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class PackagesPaymentViewModel @Inject constructor(private val paymentUseCase: PaymentUseCase) :
    ViewModel() {

    val paymentInfo = MutableLiveData<Resource<SehaPaymentResponse>>()
    fun getPaymentInfo(paymentBody: SehaPaymentBody) {
        paymentInfo.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                paymentUseCase.build(paymentBody).collect {
                    if (it.isSuccessful){
                        paymentInfo.postValue(Resource.success(data = it.body()!!))
                    }else{
                        val jObjError = JSONObject(it.errorBody()!!.string()).toString()
                        paymentInfo.postValue(Resource.error(data = null, message = jObjError))
                    }
                }
            }catch (exception:Throwable){
                paymentInfo.postValue(Resource.error(data = null, message = handleError(exception)))
            }
        }
    }

    val paymentMethods = MutableLiveData<Resource<PaymentEntity>>()
    fun getPaymentMethods(code: String) {
        paymentMethods.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                paymentUseCase.build(code).collect {
                    paymentMethods.postValue(Resource.success(data = it))
                }
            }catch (exception:Throwable){
                paymentMethods.postValue(Resource.error(data = null, message = handleError(exception)))
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
                    "هذا العضو غير موجود فى قاعدة البيانات"
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