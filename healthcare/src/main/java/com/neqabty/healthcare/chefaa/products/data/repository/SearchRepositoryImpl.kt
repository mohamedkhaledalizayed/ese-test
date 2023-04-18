package com.neqabty.healthcare.chefaa.products.data.repository

import com.neqabty.healthcare.chefaa.products.data.models.ProductItem
import com.neqabty.healthcare.chefaa.products.domain.entities.ProductEntity
import com.neqabty.healthcare.chefaa.products.domain.repository.SearchRepository
import com.neqabty.healthcare.chefaa.products.data.source.ProductSearchDs
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(private val productSearchDs: ProductSearchDs):SearchRepository {
    override fun searchForProduct(keyWord: String): Flow<List<ProductEntity>> {
        return flow {
            emit(productSearchDs.searchProduct(keyWord).map { it.toProductEntity() })
        }
    }
}

private fun ProductItem.toProductEntity(): ProductEntity {
    return ProductEntity(
        id, image, price, titleAr, titleEn, type
    )
}
