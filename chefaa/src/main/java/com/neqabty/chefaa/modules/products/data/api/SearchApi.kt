package com.neqabty.chefaa.modules.products.data.api

import com.neqabty.chefaa.modules.ChefaaResponse
import com.neqabty.chefaa.modules.products.data.models.ProductItem
import com.neqabty.chefaa.modules.products.data.models.SearchProductBody
import retrofit2.http.Body
import retrofit2.http.POST

interface SearchApi {
    @POST("search-products")
    suspend fun searchForProduct(@Body searchProductBody: SearchProductBody): ChefaaResponse<List<ProductItem>>
}