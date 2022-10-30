package com.neqabty.chefaa.modules.products.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.chefaa.core.utils.AppUtils
import com.neqabty.chefaa.core.utils.Resource
import com.neqabty.chefaa.modules.products.domain.entities.ProductEntity
import com.neqabty.chefaa.modules.products.domain.usecases.ProductSearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val searchProductUseCase: ProductSearchUseCase) :
    ViewModel() {
    val products = MutableLiveData<Resource<List<ProductEntity>>>()
    val errorMessage = MutableStateFlow("")
    fun search(keyWord: String) {
        viewModelScope.launch(Dispatchers.IO) {
            products.postValue(Resource.loading(data = null))
            try {
                searchProductUseCase.build(keyWord).collect {
                    products.postValue(Resource.success(data = it))
                }
            } catch (exception:Throwable){
                products.postValue(Resource.error(data = null, message = AppUtils().handleError(exception)))
            }
        }
    }
}

