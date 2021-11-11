package com.neqabty.yodawy.modules.products.data.model.mapper

import com.neqabty.yodawy.modules.products.data.model.ProductModel
import com.neqabty.yodawy.modules.products.domain.entity.ProductEntity

fun ProductModel.toProductEntity(): ProductEntity {
    return ProductEntity(
        active, id, image, isLimitedAvailability, isMedication, name, outOfStock, regularPrice, salePrice
    )
}
