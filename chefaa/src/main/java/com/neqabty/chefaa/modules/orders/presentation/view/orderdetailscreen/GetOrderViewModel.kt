package com.neqabty.chefaa.modules.orders.presentation.view.orderdetailscreen

//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.neqabty.chefaa.core.utils.AppUtils
//import com.neqabty.chefaa.core.utils.Resource
//import com.neqabty.chefaa.modules.orders.domain.entity.OrderEntity
//import com.neqabty.chefaa.modules.orders.domain.interactors.GetOrdersUseCase
//import com.neqabty.chefaa.modules.orders.domain.interactors.GetSpecificOrderUseCase
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.collect
//import kotlinx.coroutines.launch
//import retrofit2.HttpException
//import javax.inject.Inject
//
//@HiltViewModel
//class GetOrderViewModel @Inject constructor(private val getSpecificOrderUseCase: GetSpecificOrderUseCase) :
//    ViewModel() {
//    val order = MutableLiveData<Resource<OrderEntity>>()
//    val errorMessage = MutableStateFlow("")
//    fun getSpecificOrder(mobileNumber:String, orderId: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            order.postValue(Resource.loading(data = null))
//            try {
//                getSpecificOrderUseCase.build(mobileNumber, orderId).collect {
//                    order.postValue(Resource.success(data = it))
//                }
//            } catch (exception:Throwable){
//                order.postValue(Resource.error(data = null, message = AppUtils().handleError(exception)))
//            }
//        }
//    }
//}

