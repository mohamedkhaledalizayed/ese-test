package com.neqabty.healthcare.pharmacymart.home.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.pharmacymart.home.domain.entity.RegistrationEntity
import com.neqabty.healthcare.chefaa.home.domain.entities.orders.OrdersListEntity
import com.neqabty.healthcare.chefaa.orders.domain.usecases.GetOrdersUseCase
import com.neqabty.healthcare.core.utils.AppUtils
import com.neqabty.healthcare.core.utils.Resource
import com.neqabty.healthcare.pharmacymart.home.domain.usecases.RegisterUseCase
import com.neqabty.healthcare.pharmacymart.orders.domain.entity.orderslist.OrdersEntityList
import com.neqabty.healthcare.pharmacymart.orders.domain.usecases.GetOrdersListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PharmacyMartHomeViewModel @Inject constructor(private val registerUseCase: RegisterUseCase,
                                                    private val getOrdersUseCase: GetOrdersListUseCase
) : ViewModel() {
    val userRegistered = MutableLiveData<Resource<RegistrationEntity>>()

    fun registerUser(){
        viewModelScope.launch(Dispatchers.IO) {
            userRegistered.postValue(Resource.loading(data = null))
            try {
                registerUseCase.build().collect {
                    userRegistered.postValue(Resource.success(it))
                }
            }catch (e:Throwable){
                Log.e("error Register",e.toString())
                userRegistered.postValue(Resource.error(data = null, message = AppUtils().handleError(e)))
            }
        }
    }

    val orders = MutableLiveData<Resource<OrdersEntityList>>()
    fun getOrders() {
        viewModelScope.launch(Dispatchers.IO) {
            orders.postValue(Resource.loading(data = null))
            try {
                getOrdersUseCase.build().collect {
                    orders.postValue(Resource.success(data = it))
                }
            } catch (exception:Throwable){
                Log.e("Ordersssss",exception.toString())
                orders.postValue(Resource.error(data = null, message = AppUtils().handleError(exception)))
            }
        }
    }

}