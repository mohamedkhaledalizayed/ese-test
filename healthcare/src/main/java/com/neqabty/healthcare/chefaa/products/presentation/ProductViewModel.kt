package com.neqabty.healthcare.chefaa.products.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.chefaa.products.domain.entities.ProductEntity
import com.neqabty.healthcare.chefaa.products.domain.usecases.ProductSearchUseCase
import com.neqabty.healthcare.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException
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
                products.postValue(Resource.error(data = null, message = handleError(exception)))
            }
        }
    }

    fun handleError(throwable: Throwable): String {
        return if (throwable is HttpException) {
            when (throwable.code()) {
                400 -> {
                    "لقد تم تسجيل الدخول من قبل برجاء تسجيل الخروج واعادة المحاولة مرة اخرى"
                }
                401 -> {
                    "لقد تم تسجيل الدخول من قبل برجاء تسجيل الخروج واعادة المحاولة مرة اخرى"
                }
                403 -> {
                    "لقد تم تسجيل الدخول من قبل برجاء تسجيل الخروج واعادة المحاولة مرة اخرى"
                }
                404 -> {
                    "نأسف، لقد حدث خطأ.. برجاء المحاولة في وقت لاحق"
                }
                406 -> {
                    val errorObject = JSONObject(throwable.response()!!.errorBody()?.string())
                    errorObject.getString("message")
                }
                500 -> {
                    "نأسف، لقد حدث خطأ.. برجاء المحاولة في وقت لاحق"
                }
                else -> {
                    "No Internet Connection"
                }
            }
        } else {
           "لا توجد منتجات بهذا الاسم"
        }
    }

}

