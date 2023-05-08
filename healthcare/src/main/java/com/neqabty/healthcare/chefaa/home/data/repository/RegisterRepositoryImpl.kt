package com.neqabty.healthcare.chefaa.home.data.repository

import com.neqabty.healthcare.chefaa.home.data.source.RegisterDS
import com.neqabty.healthcare.chefaa.home.domain.entities.RegistrationEntity
import com.neqabty.healthcare.chefaa.home.domain.repository.RegisterRepository
import com.neqabty.healthcare.chefaa.orders.data.model.*
import com.neqabty.healthcare.chefaa.orders.domain.entities.ItemEntity
import com.neqabty.healthcare.chefaa.orders.domain.entities.OrderEntity
import com.neqabty.healthcare.chefaa.orders.domain.entities.OrderStatusEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(private val registerDS: RegisterDS):RegisterRepository {
    override fun registerUser(
        phoneNumber: String,
        userId: String,
        countryCode: String,
        nationalId:String,
        name:String
    ): Flow<RegistrationEntity> {
        return flow {
            emit(registerDS.registerUser(phoneNumber, userId, countryCode, nationalId, name))
        }
    }

    override fun getOrders(
        userNumber: String,
        pageNumber: Int,
        pageSize: Int
    ): Flow<List<OrderEntity>> {
        return flow {
            val orders = registerDS.getOrderList(OrderListRequestBody(userNumber))
            emit(
                orders.map { it.toOrderEntity() }
            )
        }
    }

    override fun getOrderDetails(orderId: String): Flow<List<ItemEntity>> {
        return flow {
            emit(
                registerDS.getOrder(OrderRequestBody(orderId)).map { it.toItemEntity() }
            )
        }
    }

    private fun OrderModel.toOrderEntity(): OrderEntity {
        return OrderEntity(
            addressId = addressId,
            chefaaOrderNumber = chefaaOrderNumber,
            clientId = clientId,
            countryCode = countryCode,
            createdAt = createdAt,
            deliveryFees = deliveryFees ?: 0f,
            deliveryNote = deliveryNote,
            id = id,
            orderStatus = orderStatus.toOrderStatusEntity(),
            phone = phone,
            price = price ?: 0f,
            priceBeforeDiscount = priceBeforeDiscount ?: 0f,
            status = status,
            updatedAt = updatedAt,
            userPlan = userPlan,
            items = items.map { it.toItemEntity() }
        )
    }

    private fun OrderItem.toItemEntity(): ItemEntity {
        return ItemEntity(
            addressId = addressId,
            chefaaOrderNumber = chefaaOrderNumber,
            clientId = clientId,
            createdAt = createdAt,
            id = id,
            note = note ?: "",
            orderId = orderId,
            productId = productId ?: "",
            productImage = productImage ?: "",
            type = type,
            updatedAt = updatedAt,
            quantity = quantity,
            productName = productName ?: ""
        )
    }

    private fun OrderStatus.toOrderStatusEntity(): OrderStatusEntity {
        return OrderStatusEntity(id, titleAr, titleEn ?: "")
    }

}