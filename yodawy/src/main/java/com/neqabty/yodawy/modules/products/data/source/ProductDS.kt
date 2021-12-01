package com.neqabty.yodawy.modules.products.data.source

import com.neqabty.yodawy.modules.products.data.api.ProductApi
import com.neqabty.yodawy.modules.products.data.model.ProductListResponse
import com.neqabty.yodawy.modules.products.data.model.ProductModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductDS @Inject constructor(private val productApi: ProductApi) {
    fun searchProduct(keyWord: String, pageNumber: String): Flow<ProductListResponse> {
        return flow {
            emit(productApi.searchProduct(keyWord, pageNumber).dataModel)
        }
    }
}