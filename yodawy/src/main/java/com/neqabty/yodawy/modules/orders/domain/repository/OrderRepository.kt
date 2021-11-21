package com.neqabty.yodawy.modules.orders.domain.repository

import com.neqabty.yodawy.modules.orders.domain.entity.OrderEntity
import com.neqabty.yodawy.modules.orders.domain.entity.PlaceOrderParam
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface OrderRepository {
    fun getOrder(
        mobileNumber: String,
        pageNumber: Int,
        pageSize: Int
    ): Flow<List<OrderEntity>>

    fun getSpecificOrder(
        mobileNumber: String,
        orderId: String
    ): Flow<OrderEntity>

    fun placeOrder(placeOrderParam: PlaceOrderParam): Flow<String>
    fun placePrescription(order: RequestBody, images: ArrayList<MultipartBody.Part>): Flow<String>
}