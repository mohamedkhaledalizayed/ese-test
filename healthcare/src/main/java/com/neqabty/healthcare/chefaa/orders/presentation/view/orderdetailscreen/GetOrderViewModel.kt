package com.neqabty.healthcare.chefaa.orders.presentation.view.orderdetailscreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.chefaa.orders.domain.entities.ItemEntity
import com.neqabty.healthcare.chefaa.orders.domain.usecases.GetSpecificOrderUseCase
import com.neqabty.healthcare.core.utils.AppUtils
import com.neqabty.healthcare.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetOrderViewModel @Inject constructor(private val getSpecificOrderUseCase: GetSpecificOrderUseCase) :
    ViewModel() {
    val order = MutableLiveData<Resource<List<ItemEntity>>>()
    val errorMessage = MutableStateFlow("")
    fun getSpecificOrder(orderId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            order.postValue(Resource.loading(data = null))
            try {
                getSpecificOrderUseCase.build(orderId).collect {
                    order.postValue(Resource.success(data = it))
                }
            } catch (exception:Throwable){
                order.postValue(Resource.error(data = null, message = AppUtils().handleError(exception)))
            }
        }
    }
}

