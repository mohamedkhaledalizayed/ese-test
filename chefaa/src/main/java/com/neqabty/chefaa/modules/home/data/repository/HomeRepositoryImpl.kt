package com.neqabty.chefaa.modules.home.data.repository


import com.neqabty.chefaa.modules.home.data.model.*
import com.neqabty.chefaa.modules.home.data.source.HomeDS
import com.neqabty.chefaa.modules.home.domain.entities.ItemEntity
import com.neqabty.chefaa.modules.home.domain.entities.OrderEntity
import com.neqabty.chefaa.modules.home.domain.entities.OrderStatusEntity
import com.neqabty.chefaa.modules.home.domain.entities.RegistrationEntity
import com.neqabty.chefaa.modules.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(private val homeDS: HomeDS):HomeRepository {
    override fun registerUser(
        phoneNumber: String,
        userId: String,
        countryCode: String,
        nationalId:String,
        name:String
    ): Flow<RegistrationEntity> {
        return flow {
            emit(homeDS.registerUser(phoneNumber, userId, countryCode, nationalId, name))
        }
    }

    override fun getOrders(
        userNumber: String,
        pageNumber: Int,
        pageSize: Int
    ): Flow<List<OrderEntity>> {
        return flow {
            val orders = homeDS.getOrderList(OrderListRequestBody(userNumber))
            emit(
                orders.map { it.toOrderEntity() }
            )
        }
    }

    override fun getOrderDetails(orderId: String): Flow<List<ItemEntity>> {
        return flow {
            emit(
                homeDS.getOrder(OrderRequestBody(orderId)).map { it.toItemEntity() }
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