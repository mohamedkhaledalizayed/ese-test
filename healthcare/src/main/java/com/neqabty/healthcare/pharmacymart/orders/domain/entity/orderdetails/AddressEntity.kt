package com.neqabty.healthcare.pharmacymart.orders.domain.entity.orderdetails



data class AddressEntity(
    val apartment: String,
    val buildingNo: String,
    val floor: String,
    val id: Int,
    val landLark: String,
    val lat: String,
    val long: String,
    val streetName: String,
    val title: String,
)