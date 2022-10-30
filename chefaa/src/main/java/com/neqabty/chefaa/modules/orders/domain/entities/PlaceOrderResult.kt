package com.neqabty.chefaa.modules.orders.domain.entities


data class PlaceOrderResult(
    val addressId: Int = 0,
    val chefaaOrderNumber: String = "",
    val countryCode: String = "",
    val createdAt: String = "",
    val deliveryNote: String = "",
    val id: Int = 0,
    val phone: String = "",
    val updatedAt: String = "",
    val userId: Int = 0,
    val userNumber: String = "",
    val userPlan: String = ""
)