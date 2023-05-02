package com.neqabty.healthcare.chefaa.products.domain.usecases

import com.neqabty.healthcare.chefaa.products.domain.entities.ProductEntity
import com.neqabty.healthcare.chefaa.products.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductSearchUseCase @Inject constructor(private val searchRepository: SearchRepository) {
    fun build(keyWord:String): Flow<List<ProductEntity>> {
        return searchRepository.searchForProduct(keyWord)
    }
}