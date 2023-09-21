package com.neqabty.healthcare.pharmacymart.orders.domain.usecases


import com.neqabty.healthcare.pharmacymart.orders.domain.entity.orderdetails.OrderDetailsEntity
import com.neqabty.healthcare.pharmacymart.orders.domain.repository.PharmacyMartOrdersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOrderUseCase @Inject constructor(private val ordersRepository: PharmacyMartOrdersRepository) {
    fun build(orderId: String): Flow<OrderDetailsEntity> {
        return ordersRepository.getOrderDetails(orderId)
    }
}