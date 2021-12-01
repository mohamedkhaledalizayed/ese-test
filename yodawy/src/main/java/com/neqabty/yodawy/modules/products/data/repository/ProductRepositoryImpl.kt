package com.neqabty.yodawy.modules.products.data.repository

import com.neqabty.yodawy.modules.products.data.model.ProductModel
import com.neqabty.yodawy.modules.products.data.model.mapper.toProductEntity
import com.neqabty.yodawy.modules.products.data.source.ProductDS
import com.neqabty.yodawy.modules.products.domain.entity.ProductEntity
import com.neqabty.yodawy.modules.products.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(private val productDS: ProductDS): ProductRepository{
    override fun searchProduct(keyWord: String, pageNumber: String): Flow<List<ProductEntity>> {
        return productDS.searchProduct(keyWord, pageNumber).map { it.data.map { it.toProductEntity() } }
    }

}

