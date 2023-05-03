package com.neqabty.chefaa.modules.home.domain.usecase

import com.neqabty.chefaa.modules.home.domain.entities.ItemEntity
import com.neqabty.chefaa.modules.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSpecificOrderUseCase @Inject constructor(private val homeRepository: HomeRepository) {
    fun build(orderId: String): Flow<List<ItemEntity>> {
        return homeRepository.getOrderDetails(orderId = orderId)
    }
}