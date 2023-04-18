package com.neqabty.healthcare.chefaa.products.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.chefaa.products.domain.entities.ProductEntity
import com.neqabty.healthcare.chefaa.products.domain.usecases.ProductSearchUseCase
import com.neqabty.healthcare.core.utils.AppUtils
import com.neqabty.healthcare.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
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

