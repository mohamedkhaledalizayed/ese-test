package com.neqabty.yodawy.modules.products.domain.repository

import com.neqabty.yodawy.modules.products.domain.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun searchProduct(keyWord:String): Flow<List<ProductEntity>>
}