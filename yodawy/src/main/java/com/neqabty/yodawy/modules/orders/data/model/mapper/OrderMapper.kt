package com.neqabty.yodawy.modules.orders.data.model.mapper

import com.neqabty.yodawy.modules.orders.data.model.Address
import com.neqabty.yodawy.modules.orders.data.model.Item
import com.neqabty.yodawy.modules.orders.data.model.OrderModel
import com.neqabty.yodawy.modules.orders.domain.entity.OrderAddressEntity
import com.neqabty.yodawy.modules.orders.domain.entity.OrderEntity
import com.neqabty.yodawy.modules.orders.domain.entity.OrderItemEntity


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
