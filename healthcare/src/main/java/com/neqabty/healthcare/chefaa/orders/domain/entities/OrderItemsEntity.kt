package com.neqabty.healthcare.chefaa.orders.domain.entities

import android.net.Uri
import com.neqabty.healthcare.chefaa.products.domain.entities.ProductEntity

data class OrderItemsEntity(
    val type: String,
    val quantity: Int,
    var image: String,
    val note: String,
    val productId: Int,
    val productEntity: ProductEntity? = null,
    val imageUri: Uri? = null
)