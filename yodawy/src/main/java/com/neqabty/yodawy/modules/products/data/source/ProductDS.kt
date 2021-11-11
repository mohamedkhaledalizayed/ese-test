package com.neqabty.yodawy.modules.products.data.source

import com.neqabty.yodawy.modules.products.data.api.ProductApi
import com.neqabty.yodawy.modules.products.data.model.ProductModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductDS @Inject constructor(private val productApi: ProductApi) {
    suspend fun searchProduct(keyWord:String): Flow<List<ProductModel>>{
        return productApi.searchProduct(keyWord).map { it.dataModel }
    }
}