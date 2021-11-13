package com.neqabty.yodawy.modules.orders.presentation.view.placeorderscreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.yodawy.modules.orders.domain.entity.ItemParam
import com.neqabty.yodawy.modules.orders.domain.entity.PlaceOrderParam
import com.neqabty.yodawy.modules.orders.domain.interactors.PlaceOrderUseCase
import com.neqabty.yodawy.modules.products.domain.entity.ProductEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaceOrderViewModel @Inject constructor(private val placeOrderUseCase: PlaceOrderUseCase) :
    ViewModel() {
    val placeOrderResult = MutableLiveData<String>()
    fun placeOrder(
        adressId: String,
        mobileNumber: String,
        notes: String,
        plan: String,
        items: List<ItemParam>
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                placeOrderUseCase.build(
                    PlaceOrderParam(addressId = adressId,
                        mobile = mobileNumber,
                        notes = notes,
                        plan = plan,
                        itemParams = items)
                ).collect {
                    placeOrderResult.postValue(it)
                }
            } catch (e: Throwable) {

            }
        }
    }
}