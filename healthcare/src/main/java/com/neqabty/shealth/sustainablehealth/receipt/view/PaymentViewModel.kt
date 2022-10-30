package com.neqabty.shealth.sustainablehealth.receipt.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.shealth.core.utils.Resource
import com.neqabty.shealth.sustainablehealth.receipt.domain.entity.paymentstatus.PaymentStatusEntity
import com.neqabty.shealth.sustainablehealth.receipt.domain.interactors.PaymentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(private val paymentUseCase: PaymentUseCase) :
    ViewModel() {

    val paymentStatus = MutableLiveData<Resource<PaymentStatusEntity>>()
    fun getPaymentStatus(referenceCode: String) {
        paymentStatus.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                paymentUseCase.build(referenceCode).collect {
                    paymentStatus.postValue(Resource.success(data = it))
                }
            }catch (exception:Throwable){
                paymentStatus.postValue(Resource.error(data = null, message = handleError(exception)))
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
                    throwable.message!!
                }
            }
        } else {
            ""
        }
    }
}