package com.neqabty.healthcare.pharmacymart.orders.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.core.utils.AppUtils
import com.neqabty.healthcare.core.utils.Resource
import com.neqabty.healthcare.pharmacymart.orders.domain.entity.addorder.AddOrderEntity
import com.neqabty.healthcare.pharmacymart.orders.domain.entity.cancelorder.CancelOrderEntity
import com.neqabty.healthcare.pharmacymart.orders.domain.entity.confirmorder.ConfirmOrderEntity
import com.neqabty.healthcare.pharmacymart.orders.domain.entity.orderdetails.OrderDetailsEntity
import com.neqabty.healthcare.pharmacymart.orders.domain.usecases.AddOrderUseCase
import com.neqabty.healthcare.pharmacymart.orders.domain.usecases.CancelOrderUseCase
import com.neqabty.healthcare.pharmacymart.orders.domain.usecases.ConfirmOrderUseCase
import com.neqabty.healthcare.pharmacymart.orders.domain.usecases.GetOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(private val placeOrderUseCase: AddOrderUseCase,
                                          private val getOrderUseCase: GetOrderUseCase,
                                          private val cancelOrderUseCase: CancelOrderUseCase,
                                          private val confirmOrderUseCase: ConfirmOrderUseCase) :
    ViewModel() {

    val placeImagesResult = MutableLiveData<Resource<AddOrderEntity>>()
    fun placePrescriptionImages(list: List<String>, addressId:Int, deviceInfo:String, currentLocation:String, deliveryNote: String, deliveryMobile: String, orderByText: String) {
        viewModelScope.launch(Dispatchers.IO) {
            placeImagesResult.postValue(Resource.loading(data = null))
            try {
                placeOrderUseCase.build(
                    items = list,
                    addressId = addressId,
                    deliveryNote = deliveryNote,
                    deliveryMobile = deliveryMobile,
                    orderByText = orderByText,
                    deviceInfo = deviceInfo,
                    currentLocation = currentLocation).collect {
                    placeImagesResult.postValue(Resource.success(it))
                }
            } catch (exception: Throwable) {
                placeImagesResult.postValue(Resource.error(data = null, message = AppUtils().handleError(exception)))
            }
        }
    }

    val order = MutableLiveData<Resource<OrderDetailsEntity>>()
    fun getOrder(orderId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            order.postValue(Resource.loading(data = null))
            try {
                getOrderUseCase.build(orderId).collect {
                    order.postValue(Resource.success(it))
                }
            } catch (exception: Throwable) {
                order.postValue(Resource.error(data = null, message = AppUtils().handleError(exception)))
            }
        }
    }

    val cancellationStatus = MutableLiveData<Resource<CancelOrderEntity>>()
    fun cancelOrder(orderId: String, cancellationReason: String) {
        viewModelScope.launch(Dispatchers.IO) {
            cancellationStatus.postValue(Resource.loading(data = null))
            try {
                cancelOrderUseCase.build(orderId, cancellationReason).collect {
                    cancellationStatus.postValue(Resource.success(it))
                }
            } catch (exception: Throwable) {
                cancellationStatus.postValue(Resource.error(data = null, message = AppUtils().handleError(exception)))
            }
        }
    }

    val confirmationStatus = MutableLiveData<Resource<ConfirmOrderEntity>>()
    fun confirmOrder(orderId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            confirmationStatus.postValue(Resource.loading(data = null))
            try {
                confirmOrderUseCase.build(orderId).collect {
                    confirmationStatus.postValue(Resource.success(it))
                }
            } catch (exception: Throwable) {
                confirmationStatus.postValue(Resource.error(data = null, message = AppUtils().handleError(exception)))
            }
        }
    }

}