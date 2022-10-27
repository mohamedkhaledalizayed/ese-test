package com.neqabty.chefaa.modules.orders.domain.entities

import android.net.Uri
import com.neqabty.chefaa.modules.products.domain.entities.ProductEntity

data class OrderItemsEntity(
    val type: String,
    val quantity: Int,
    val image: String,
    val note: String,
    val productId: Int,
    val productEntity: ProductEntity? = null,
    val imageUri: Uri? = null
)