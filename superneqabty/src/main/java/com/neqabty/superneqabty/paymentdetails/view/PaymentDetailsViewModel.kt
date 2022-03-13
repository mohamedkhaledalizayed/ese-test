package com.neqabty.superneqabty.paymentdetails.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.superneqabty.core.utils.AppUtils
import com.neqabty.superneqabty.core.utils.Resource
import com.neqabty.superneqabty.paymentdetails.data.model.PaymentBody
import com.neqabty.superneqabty.paymentdetails.data.model.ReceiptResponse
import com.neqabty.superneqabty.paymentdetails.data.model.payment.PaymentResponse
import com.neqabty.superneqabty.paymentdetails.domain.interactors.GetPaymentDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
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
                payment.postValue(Resource.error(data = null, message = AppUtils().handleError(exception)))
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
}