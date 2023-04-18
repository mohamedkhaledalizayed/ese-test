package com.neqabty.healthcare.chefaa.products.data.api

import com.neqabty.healthcare.chefaa.ChefaaResponse
import com.neqabty.healthcare.chefaa.products.data.models.ProductItem
import com.neqabty.healthcare.chefaa.products.data.models.SearchProductBody
import retrofit2.http.Body
import retrofit2.http.POST

interface SearchApi {
    @POST("search-products")
    suspend fun searchForProduct(@Body searchProductBody: SearchProductBody): ChefaaResponse<List<ProductItem>?>
}