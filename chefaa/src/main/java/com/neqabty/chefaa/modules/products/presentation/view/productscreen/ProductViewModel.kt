package com.neqabty.chefaa.modules.products.presentation.view.productscreen
//
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.neqabty.chefaa.core.utils.AppUtils
//import com.neqabty.chefaa.core.utils.Resource
//import com.neqabty.chefaa.modules.address.domain.entity.UserEntity
//import com.neqabty.chefaa.modules.address.domain.interactors.GetUserUseCase
//import com.neqabty.chefaa.modules.address.domain.params.GetUserUseCaseParams
//import com.neqabty.chefaa.modules.products.domain.entity.ProductEntity
//import com.neqabty.chefaa.modules.products.domain.interactors.SearchProductUseCase
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.collect
//import kotlinx.coroutines.launch
//import javax.inject.Inject
//
//@HiltViewModel
//class ProductViewModel @Inject constructor(private val searchProductUseCase: SearchProductUseCase) :
//    ViewModel() {
//    val products = MutableLiveData<Resource<List<ProductEntity>>>()
//    val errorMessage = MutableStateFlow("")
//    fun search(keyWord: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            products.postValue(Resource.loading(data = null))
//            try {
//                searchProductUseCase.build(keyWord).collect {
//                    products.postValue(Resource.success(data = it))
//                }
//            } catch (exception:Throwable){
//                products.postValue(Resource.error(data = null, message = AppUtils().handleError(exception)))
//            }
//        }
//    }
//}
//
