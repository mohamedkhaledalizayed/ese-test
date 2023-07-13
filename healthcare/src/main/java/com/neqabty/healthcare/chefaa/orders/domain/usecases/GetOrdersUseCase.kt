package com.neqabty.healthcare.chefaa.orders.domain.usecases



import com.neqabty.healthcare.chefaa.orders.domain.entities.OrderEntity
import com.neqabty.healthcare.chefaa.orders.domain.entities.orders.ChefaaOrdersEntity
import com.neqabty.healthcare.chefaa.orders.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOrdersUseCase @Inject constructor(private val orderRepository: OrderRepository) {
    fun build(userNumber:String,pageSize:Int,pageNumber:Int): Flow<ChefaaOrdersEntity> {
        return orderRepository.getOrders(userNumber = userNumber,pageSize =pageSize ,pageNumber =pageNumber )
    }
}