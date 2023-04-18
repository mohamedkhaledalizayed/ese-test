package com.neqabty.healthcare.chefaa.products.data.source

import com.neqabty.healthcare.chefaa.products.data.api.SearchApi
import com.neqabty.healthcare.chefaa.products.data.models.ProductItem
import com.neqabty.healthcare.chefaa.products.data.models.SearchProductBody
import javax.inject.Inject

class ProductSearchDs @Inject constructor(private val searchApi: SearchApi) {
    suspend fun searchProduct(keyWord: String): List<ProductItem> {
        return searchApi.searchForProduct(SearchProductBody(productName = keyWord)).responseData!!
    }
}