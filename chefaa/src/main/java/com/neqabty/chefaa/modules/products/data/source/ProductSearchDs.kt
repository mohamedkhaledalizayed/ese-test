package com.neqabty.chefaa.modules.products.data.source

import com.neqabty.chefaa.modules.ChefaaResponse
import com.neqabty.chefaa.modules.products.data.api.SearchApi
import com.neqabty.chefaa.modules.products.data.models.ProductItem
import com.neqabty.chefaa.modules.products.data.models.SearchProductBody
import javax.inject.Inject

class ProductSearchDs @Inject constructor(private val searchApi: SearchApi) {
    suspend fun searchProduct(keyWord: String): List<ProductItem> {
        return searchApi.searchForProduct(SearchProductBody(productName = keyWord)).responseData
    }
}