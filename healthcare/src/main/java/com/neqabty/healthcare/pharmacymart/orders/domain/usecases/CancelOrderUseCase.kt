package com.neqabty.healthcare.pharmacymart.orders.domain.usecases


import com.neqabty.healthcare.pharmacymart.orders.domain.entity.cancelorder.CancelOrderEntity
import com.neqabty.healthcare.pharmacymart.orders.domain.entity.orderdetails.OrderDetailsEntity
import com.neqabty.healthcare.pharmacymart.orders.domain.repository.PharmacyMartOrdersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CancelOrderUseCase @Inject constructor(private val ordersRepository: PharmacyMartOrdersRepository) {
    fun build(orderId: String, cancellationReason: String): Flow<CancelOrderEntity> {
        return ordersRepository.cancelOrder(orderId, cancellationReason)
    }
}