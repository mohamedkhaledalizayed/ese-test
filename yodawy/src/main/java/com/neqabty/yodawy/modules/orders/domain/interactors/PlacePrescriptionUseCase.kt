package com.neqabty.yodawy.modules.orders.domain.interactors

import com.neqabty.yodawy.modules.orders.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class PlacePrescriptionUseCase @Inject constructor(private val orderRepository: OrderRepository) {
     fun build(order: RequestBody, images: ArrayList<MultipartBody.Part>): Flow<String> {
         return orderRepository.placePrescription(order, images)
     }
}