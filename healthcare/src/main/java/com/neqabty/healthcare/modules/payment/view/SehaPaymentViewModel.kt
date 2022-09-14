package com.neqabty.healthcare.modules.payment.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.core.utils.AppUtils
import com.neqabty.core.utils.Resource
import com.neqabty.healthcare.modules.payment.data.model.SehaPaymentBody
import com.neqabty.healthcare.modules.payment.domain.entity.SehaPaymentEntity
import com.neqabty.healthcare.modules.payment.domain.usecase.SehaPaymentUseCase
import com.neqabty.meganeqabty.payment.data.model.PaymentBody
import com.neqabty.meganeqabty.payment.data.model.PaymentHomeBody
import com.neqabty.meganeqabty.payment.domain.entity.branches.BranchesEntity
import com.neqabty.meganeqabty.payment.domain.entity.inquiryresponse.ReceiptDataEntity
import com.neqabty.meganeqabty.payment.domain.entity.payment.PaymentEntity
import com.neqabty.meganeqabty.payment.domain.entity.paymentmethods.PaymentMethodEntity
import com.neqabty.meganeqabty.payment.domain.entity.paymentstatus.PaymentStatusEntity
import com.neqabty.meganeqabty.payment.domain.entity.serviceactions.ServiceActionsEntity
import com.neqabty.meganeqabty.payment.domain.entity.services.ServicesListEntity
import com.neqabty.meganeqabty.payment.domain.interactors.PaymentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class SehaPaymentViewModel @Inject constructor(private val paymentUseCase: SehaPaymentUseCase) :
    ViewModel() {

    val paymentInfo = MutableLiveData<Resource<SehaPaymentEntity>>()
    fun getPaymentInfo(paymentBody: SehaPaymentBody) {
        paymentInfo.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                paymentUseCase.build(paymentBody).collect {
                    paymentInfo.postValue(Resource.success(data = it))
                }
            }catch (exception:Throwable){
                paymentInfo.postValue(Resource.error(data = null, message = AppUtils().handleError(exception)))
            }
        }
    }

    val paymentMethods = MutableLiveData<Resource<List<PaymentMethodEntity>>>()
    fun getPaymentMethods() {
        paymentMethods.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                paymentUseCase.build().collect {
                    paymentMethods.postValue(Resource.success(data = it))
                }
            }catch (exception:Throwable){
                paymentMethods.postValue(Resource.error(data = null, message = AppUtils().handleError(exception)))
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