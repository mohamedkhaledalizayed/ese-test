package com.neqabty.healthcare.chefaa.products.data.api

import com.neqabty.healthcare.chefaa.ChefaaResponse
import com.neqabty.healthcare.chefaa.products.data.models.ProductItem
import com.neqabty.healthcare.chefaa.products.data.models.SearchProductBody
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface SearchApi {
    @POST("healthcare/api/v1/chefaa/search-products")
    suspend fun searchForProduct(@Header("Authorization") token: String, @Body searchProductBody: SearchProductBody): ChefaaResponse<List<ProductItem>?>
}