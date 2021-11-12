package com.neqabty.yodawy.modules.products.data.api

import com.neqabty.yodawy.modules.address.data.model.Response
import com.neqabty.yodawy.modules.products.data.model.ProductModel
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.POST

interface ProductApi {
    @POST("search/products")
    suspend fun searchProduct(@Body keyWord: String): Response<List<ProductModel>>
}