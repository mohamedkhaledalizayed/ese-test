package com.neqabty.healthcare.pharmacymart.orders.domain.usecases


import com.neqabty.healthcare.pharmacymart.orders.domain.entity.confirmorder.ConfirmOrderEntity
import com.neqabty.healthcare.pharmacymart.orders.domain.repository.PharmacyMartOrdersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ConfirmOrderUseCase @Inject constructor(private val ordersRepository: PharmacyMartOrdersRepository) {
    fun build(orderId: String): Flow<ConfirmOrderEntity> {
        return ordersRepository.confirmOrder(orderId)
    }
}