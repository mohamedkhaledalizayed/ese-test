package com.neqabty.healthcare.mega.payment.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.core.utils.AppUtils
import com.neqabty.healthcare.core.utils.Resource
import com.neqabty.healthcare.mega.payment.data.model.inquiryresponse.ReceiptResponse
import com.neqabty.healthcare.mega.payment.domain.entity.branches.BranchesEntity
import com.neqabty.healthcare.mega.payment.domain.entity.payment.PaymentEntity
import com.neqabty.healthcare.mega.payment.domain.entity.paymentstatus.PaymentStatusEntity
import com.neqabty.healthcare.mega.payment.domain.entity.serviceactions.ServiceActionsEntity
import com.neqabty.healthcare.mega.payment.domain.entity.services.ServicesListEntity
import com.neqabty.healthcare.mega.payment.domain.interactors.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val servicesUseCase: GetServicesUseCase,
    private val serviceActionsUseCase: GetServiceActionsUseCase,
    private val paymentDetailsUseCase: GetPaymentDetailsUseCase,
    private val paymentUseCase: PaymentUseCase,
    private val paymentStatusUseCase: GetPaymentStatusUseCase,
    private val branchesUseCase: GetBranchesUseCase
) :
    ViewModel() {

    val services = MutableLiveData<Resource<List<ServicesListEntity>>>()
    fun getServices() {
        services.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                servicesUseCase.getServices().collect {
                    services.postValue(Resource.success(data = it))
                }
            }catch (exception:Throwable){
                services.postValue(Resource.error(data = null, message = handleError(exception)))
            }
        }
    }

    val serviceActions = MutableLiveData<Resource<List<ServiceActionsEntity>>>()
    fun getServiceActions(code: String) {
        serviceActions.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                serviceActionsUseCase.getServiceActions(code).collect {
                    serviceActions.postValue(Resource.success(data = it))
                }
            }catch (exception:Throwable){
                serviceActions.postValue(Resource.error(data = null, message = handleError(exception)))
            }
        }
    }

    val payment = MutableLiveData<Resource<ReceiptResponse>>()
    fun getPaymentDetails(id: String, code: String, number: String) {
        payment.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                paymentDetailsUseCase.build(id, code, number).collect {
                    if (it.isSuccessful){
                        payment.postValue(Resource.success(data = it.body()!!))
                    }else{
                        val jObjError = JSONObject(it.errorBody()!!.string()).toString()

                        payment.postValue(Resource.error(data = null, message = "3ak # $jObjError"))
                    }

                }
            }catch (exception: Exception){
                payment.postValue(Resource.error(data = null, message = handleError(exception)))
            }
        }
    }

    val paymentInfo = MutableLiveData<Resource<PaymentEntity>>()

    fun getPaymentInfo(paymentBody: Any) {
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

    val paymentStatus = MutableLiveData<Resource<PaymentStatusEntity>>()
    fun getPaymentStatus(referenceCode: String) {
        paymentStatus.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                paymentStatusUseCase.build(referenceCode).collect {
                    paymentStatus.postValue(Resource.success(data = it))
                }
            }catch (exception:Throwable){
                paymentStatus.postValue(Resource.error(data = null, message = handleError(exception)))
            }
        }
    }

    val branches = MutableLiveData<Resource<List<BranchesEntity>>>()
    fun getBranches() {
        branches.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                branchesUseCase.getBranches().collect {
                    branches.postValue(Resource.success(data = it))
                }
            }catch (exception:Throwable){
                branches.postValue(Resource.error(data = null, message = handleError(exception)))
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
            throwable.message!!
        }
    }
}