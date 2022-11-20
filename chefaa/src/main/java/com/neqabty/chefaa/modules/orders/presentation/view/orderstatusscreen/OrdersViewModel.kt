package com.neqabty.chefaa.modules.orders.presentation.view.orderstatusscreen

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.chefaa.core.utils.AppUtils
import com.neqabty.chefaa.core.utils.Resource
import com.neqabty.chefaa.modules.orders.domain.entities.OrderClientEntity
import com.neqabty.chefaa.modules.orders.domain.entities.OrderEntity
import com.neqabty.chefaa.modules.orders.domain.usecases.GetOrdersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(private val getOrdersUseCase: GetOrdersUseCase) :
    ViewModel() {
    val orders = MutableLiveData<Resource<List<OrderEntity>>>()
    val errorMessage = MutableStateFlow("")
    fun getOrders(mobileNumber:String,pageSize:Int,pageNumber:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            orders.postValue(Resource.loading(data = null))
            try {
                getOrdersUseCase.build(mobileNumber, pageSize, pageNumber).collect {
                    orders.postValue(Resource.success(data = it))
                }
            } catch (exception:Throwable){
                Log.e("Ordersssss",exception.toString())
                orders.postValue(Resource.error(data = null, message = AppUtils().handleError(exception)))
            }
        }
    }
}
