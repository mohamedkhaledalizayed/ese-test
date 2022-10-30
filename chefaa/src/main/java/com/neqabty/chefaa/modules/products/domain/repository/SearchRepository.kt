package com.neqabty.chefaa.modules.products.domain.repository

import com.neqabty.chefaa.modules.products.domain.entities.ProductEntity
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun searchForProduct(keyWord:String): Flow<List<ProductEntity>>
}