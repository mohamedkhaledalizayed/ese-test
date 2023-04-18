package com.neqabty.healthcare.chefaa.orders.domain.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemEntity(
    val addressId: String ,
    val chefaaOrderNumber: String ,
    val clientId: String ,
    val createdAt: String ,
    val id: Int ,
    val note: String ,
    val orderId: Int ,
    val productId: String ,
    val productImage: String,
    val productName: String,
    val quantity: String ,
    val type: String ,
    val updatedAt: String
) : Parcelable
