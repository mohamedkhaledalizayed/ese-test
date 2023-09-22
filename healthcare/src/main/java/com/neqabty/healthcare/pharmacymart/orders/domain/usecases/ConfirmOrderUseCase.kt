package com.neqabty.healthcare.pharmacymart.orders.domain.usecases


import com.neqabty.healthcare.pharmacymart.orders.domain.repository.PharmacyMartOrdersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ConfirmOrderUseCase @Inject constructor(private val ordersRepository: PharmacyMartOrdersRepository) {
    fun build(orderId: String): Flow<String> {
        return ordersRepository.confirmOrder(orderId)
    }
}