package com.neqabty.yodawy.modules.products.domain.repository

import com.neqabty.yodawy.modules.products.domain.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun searchProduct(keyWord:String, pageNumber: String): Flow<List<ProductEntity>>
}