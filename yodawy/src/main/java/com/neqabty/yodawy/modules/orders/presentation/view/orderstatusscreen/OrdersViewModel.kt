package com.neqabty.yodawy.modules.orders.presentation.view.orderstatusscreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.yodawy.modules.orders.domain.entity.OrderEntity
import com.neqabty.yodawy.modules.orders.domain.interactors.GetOrdersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(private val getOrdersUseCase: GetOrdersUseCase) :
    ViewModel() {
    val orders = MutableLiveData<List<OrderEntity>>()
    val errorMessage = MutableStateFlow("")
    fun getOrders(mobileNumber:String,pageSize:Int,pageNumber:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getOrdersUseCase.build(mobileNumber, pageSize, pageNumber).collect {
                    orders.postValue(it)
                }
            } catch (e:Throwable){
                errorMessage.emit(e.toString())
            }
        }
    }
}

