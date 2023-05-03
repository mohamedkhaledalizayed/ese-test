package com.neqabty.chefaa.modules.home.domain.usecase

import com.neqabty.chefaa.modules.home.domain.entities.OrderEntity
import com.neqabty.chefaa.modules.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOrdersUseCase @Inject constructor(private val homeRepository: HomeRepository) {
    fun build(userNumber:String,pageSize:Int,pageNumber:Int): Flow<List<OrderEntity>> {
        return homeRepository.getOrders(userNumber = userNumber,pageSize =pageSize ,pageNumber =pageNumber )
    }
}