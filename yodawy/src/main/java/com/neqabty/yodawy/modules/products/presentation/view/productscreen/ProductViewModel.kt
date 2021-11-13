package com.neqabty.yodawy.modules.products.presentation.view.productscreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.yodawy.modules.address.domain.entity.UserEntity
import com.neqabty.yodawy.modules.address.domain.interactors.GetUserUseCase
import com.neqabty.yodawy.modules.address.domain.params.GetUserUseCaseParams
import com.neqabty.yodawy.modules.products.domain.entity.ProductEntity
import com.neqabty.yodawy.modules.products.domain.interactors.SearchProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val searchProductUseCase: SearchProductUseCase) :
    ViewModel() {
    val products = MutableLiveData<List<ProductEntity>>()
    val errorMessage = MutableStateFlow("")
    fun search(keyWord: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                searchProductUseCase.build(keyWord).collect {
                    products.postValue(it)
                }
            } catch (e:Throwable){
                errorMessage.emit(e.toString())
            }
        }
    }
}

