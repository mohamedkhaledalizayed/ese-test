package com.neqabty.yodawy.modules.products.domain.interactors

import com.neqabty.yodawy.modules.products.domain.entity.ProductEntity
import com.neqabty.yodawy.modules.products.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchProductUseCase @Inject constructor(private val productRepository: ProductRepository) {
    suspend fun build(params:String):Flow<List<ProductEntity>>{
        return productRepository.searchProduct(params)
    }
}