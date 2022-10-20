package com.neqabty.chefaa.modules.products.domain.usecases

import com.neqabty.chefaa.modules.products.domain.entities.ProductEntity
import com.neqabty.chefaa.modules.products.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductSearchUseCase @Inject constructor(private val searchRepository: SearchRepository) {
    fun build(keyWord:String): Flow<List<ProductEntity>> {
        return searchRepository.searchForProduct(keyWord)
    }
}