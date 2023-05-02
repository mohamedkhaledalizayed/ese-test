package com.neqabty.healthcare.chefaa.products.domain.repository

import com.neqabty.healthcare.chefaa.products.domain.entities.ProductEntity
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun searchForProduct(keyWord:String): Flow<List<ProductEntity>>
}