package com.neqabty.meganeqabty.paymentdetails.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.meganeqabty.core.utils.AppUtils
import com.neqabty.meganeqabty.core.utils.Resource
import com.neqabty.meganeqabty.paymentdetails.data.model.PaymentBody
import com.neqabty.meganeqabty.paymentdetails.data.model.inquiryresponse.ReceiptResponse
import com.neqabty.meganeqabty.paymentdetails.data.model.payment.PaymentResponse
import com.neqabty.meganeqabty.paymentdetails.data.model.paymentdetails.PaymentDetails
import com.neqabty.meganeqabty.paymentdetails.data.model.paymentmethods.PaymentMethodsResponse
import com.neqabty.meganeqabty.paymentdetails.domain.interactors.GetPaymentDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class PaymentDetailsViewModel @Inject constructor(private val getPaymentDetailsUseCase: GetPaymentDetailsUseCase) :
    ViewModel() {
    val payment = MutableLiveData<Resource<ReceiptResponse>>()
    fun getPaymentDetails(id: String, number: String) {
        payment.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getPaymentDetailsUseCase.build(id, number).collect {
                    payment.postValue(Resource.success(data = it))
                }
            }catch (exception:Throwable){
                payment.postValue(Resource.error(data = null, message = handleError(exception)))
            }
        }
    }

    val paymentInfo = MutableLiveData<Resource<PaymentResponse>>()
    fun getPaymentInfo(paymentBody: PaymentBody) {
        paymentInfo.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getPaymentDetailsUseCase.build(paymentBody).collect {
                    paymentInfo.postValue(Resource.success(data = it))
                }
            }catch (exception:Throwable){
                paymentInfo.postValue(Resource.error(data = null, message = AppUtils().handleError(exception)))
            }
        }
    }

    val paymentMethods = MutableLiveData<Resource<PaymentMethodsResponse>>()
    fun getPaymentMethods() {
        paymentMethods.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getPaymentDetailsUseCase.build().collect {
                    paymentMethods.postValue(Resource.success(data = it))
                }
            }catch (exception:Throwable){
                paymentMethods.postValue(Resource.error(data = null, message = AppUtils().handleError(exception)))
            }
        }
    }

    val paymentDetails = MutableLiveData<Resource<PaymentDetails>>()
    fun getPaymentDetails(referenceCode: String) {
        paymentDetails.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getPaymentDetailsUseCase.build(referenceCode).collect {
                    paymentDetails.postValue(Resource.success(data = it))
                }
            }catch (exception:Throwable){
                paymentDetails.postValue(Resource.error(data = null, message = AppUtils().handleError(exception)))
            }
        }
    }

    private fun handleError(throwable: Throwable): String {
        return if (throwable is HttpException) {
            when (throwable.code()) {
                400 -> {
                    "الرجاء تجديد ترخيص الوزارة"
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