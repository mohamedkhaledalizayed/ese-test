package com.neqabty.yodawy.modules.products.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductEntity(
    val active: Boolean,
    val id: String,
    val image: String,
    val isLimitedAvailability: Boolean,
    val isMedication: Boolean,
    val name: String,
    val outOfStock: Boolean,
    val regularPrice: Float,
    val salePrice: Float,
    var quantity: Int = 1
): Parcelable
