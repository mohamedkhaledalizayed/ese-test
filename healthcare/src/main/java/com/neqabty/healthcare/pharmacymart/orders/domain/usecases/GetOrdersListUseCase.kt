package com.neqabty.healthcare.pharmacymart.orders.domain.usecases


import com.neqabty.healthcare.pharmacymart.orders.domain.entity.orderslist.OrdersEntityList
import com.neqabty.healthcare.pharmacymart.orders.domain.repository.PharmacyMartOrdersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOrdersListUseCase @Inject constructor(private val ordersRepository: PharmacyMartOrdersRepository) {
    fun build(): Flow<OrdersEntityList> {
        return ordersRepository.getOrders()
    }
}