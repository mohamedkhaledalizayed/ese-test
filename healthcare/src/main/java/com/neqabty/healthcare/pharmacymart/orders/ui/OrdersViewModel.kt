package com.neqabty.healthcare.pharmacymart.orders.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.core.utils.AppUtils
import com.neqabty.healthcare.core.utils.Resource
import com.neqabty.healthcare.pharmacymart.orders.domain.entity.addorder.AddOrderEntity
import com.neqabty.healthcare.pharmacymart.orders.domain.usecases.AddOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(private val placeOrderUseCase: AddOrderUseCase) :
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

}