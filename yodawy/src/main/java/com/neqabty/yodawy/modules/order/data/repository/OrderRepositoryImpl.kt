package com.neqabty.yodawy.modules.order.data.repository

import com.neqabty.yodawy.modules.order.data.model.Address
import com.neqabty.yodawy.modules.order.data.model.Item
import com.neqabty.yodawy.modules.order.data.model.OrderListRequestBody
import com.neqabty.yodawy.modules.order.data.model.OrderModel
import com.neqabty.yodawy.modules.order.data.source.OrdersDS
import com.neqabty.yodawy.modules.order.domain.entity.OrderAddressEntity
import com.neqabty.yodawy.modules.order.domain.entity.OrderEntity
import com.neqabty.yodawy.modules.order.domain.entity.OrderItemEntity
import com.neqabty.yodawy.modules.order.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(private val ordersDS: OrdersDS) : OrderRepository {
    override suspend fun getOrder(
        mobileNumber: String,
        pageNumber: Int,
        pageSize: Int
    ): Flow<List<OrderEntity>> {
        return ordersDS.getOrderList(
            OrderListRequestBody(
                mobile = mobileNumber,
                pageNumber = pageNumber,
                pageSize = pageSize
            )
        ).map { it.orders.map { it.toOrderEntity() } }
    }
}

fun OrderModel.toOrderEntity(): OrderEntity {
    return OrderEntity(
        address = this.address.toOrderAddressEntity(),
        cancellationReasons,
        creationDate,
        currentStatus,
        deliveryFees,
        id,
        items.toOderItemsEntity(),
        notes, orderNumber, orderPrice, pharmacy, prescriptionImages
    )
}

private fun List<Item>.toOderItemsEntity(): List<OrderItemEntity> {
    return this.map {
        OrderItemEntity(
            actualWoocommerceProductId = it.actualWoocommerceProductId,
            alternativesSKUs = it.alternativesSKUs,
            available = it.available,
            channel = it.channel,
            coveragePercentage = it.coveragePercentage,
            coverageValue = it.coverageValue,
            deliveryTimeValue = it.deliveryTimeValue,
            drug = it.drug,
            drugDosageFormId = it.drugDosageFormId,
            drugId = it.drugId,
            drugName = it.drugName,
            drugTotal = it.drugTotal,
            drugTotalAfterCoverage = it.drugTotalAfterCoverage,
            maximumQuantityPerUser = it.maximumQuantityPerUser,
            price = it.price,
            priceBeforeSale = it.priceBeforeSale,
            productType = it.productType,
            quantity = it.quantity,
            selectedUnit = it.selectedUnit,
            variationSKU = it.variationSKU,
            wasLimited = it.wasLimited,
            woocommerceOrderAttributes = it.woocommerceOrderAttributes,
            woocommerceProductId = it.woocommerceProductId,
            yodawyDeal = it.yodawyDeal
        )
    }
}

private fun Address.toOrderAddressEntity(): OrderAddressEntity {
    return OrderAddressEntity(address, addressName, id)
}
