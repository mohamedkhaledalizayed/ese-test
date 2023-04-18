package com.neqabty.healthcare.chefaa.orders.presentation.placeprescriptionscreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.chefaa.orders.domain.entities.PlaceOrderResult
import com.neqabty.healthcare.chefaa.orders.domain.usecases.PlaceOrderUseCase
import com.neqabty.healthcare.core.data.Constants.cart
import com.neqabty.healthcare.core.data.Constants.mobileNumber
import com.neqabty.healthcare.core.utils.AppUtils
import com.neqabty.healthcare.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaceOrderViewModel @Inject constructor(private val placeOrderUseCase: PlaceOrderUseCase) :
    ViewModel() {
    val placeImagesResult = MutableLiveData<Resource<PlaceOrderResult>>()
    fun placePrescriptionImages(addressId:Int, deviceInfo:String, currentLocation:String, deliveryNote: String) {
        viewModelScope.launch(Dispatchers.IO) {
            placeImagesResult.postValue(Resource.loading(data = null))
            try {
                placeOrderUseCase.build(cart.getOrderItems(),addressId, mobileNumber, deliveryNote = deliveryNote, deviceInfo, currentLocation).collect {
                    placeImagesResult.postValue(Resource.success(it))
                }
            } catch (exception: Throwable) {
                placeImagesResult.postValue(Resource.error(data = null, message = AppUtils().handleError(exception)))
            }
        }
    }

}