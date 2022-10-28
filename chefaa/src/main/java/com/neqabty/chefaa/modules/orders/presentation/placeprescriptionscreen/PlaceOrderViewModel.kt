package com.neqabty.chefaa.modules.orders.presentation.placeprescriptionscreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.chefaa.core.data.Constants.cart
import com.neqabty.chefaa.core.data.Constants.mobileNumber
import com.neqabty.chefaa.core.utils.AppUtils
import com.neqabty.chefaa.core.utils.Resource
import com.neqabty.chefaa.modules.orders.domain.usecases.PlaceOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class PlaceOrderViewModel @Inject constructor(private val placeOrderUseCase: PlaceOrderUseCase) :
    ViewModel() {
    val placeImagesResult = MutableLiveData<Resource<String>>()
    fun placePrescriptionImages(addressId:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            placeImagesResult.postValue(Resource.loading(data = null))
            try {
                placeOrderUseCase.build(cart.getOrderItems(),addressId, mobileNumber, deliveryNote = "").collect {
                    placeImagesResult.postValue(Resource.success(it.chefaaOrderNumber))
                }
            } catch (exception: Throwable) {
                placeImagesResult.postValue(Resource.error(data = null, message = AppUtils().handleError(exception)))
            }
        }
    }

}