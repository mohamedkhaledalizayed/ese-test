package com.neqabty.healthcare.chefaa.address.domain.entities

data class AddressEntity(
    val address: String = "",
    val apartmentNo: String = "",
    val areaAr: String = "",
    val areaEn: String = "",
    val buildingNo: String = "",
    val cityAr: String = "",
    val cityEn: String = "",
    val floorNo: String = "",
    val id: Int = 0,
    val landmark: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val title: String = ""
)