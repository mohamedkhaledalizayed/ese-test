package com.neqabty.healthcare.pharmacymart.address.domain.entity

data class PharmacyMartAddressEntity(
    val address: String = "",
    val apartmentNo: String = "",
    val streetName: String = "",
    val areaAr: String = "",
    val areaEn: String = "",
    val buildingNo: String = "",
    val cityAr: String = "",
    val cityEn: String = "",
    val floorNo: String = "",
    val id: Int = 0,
    val landmark: String = "",
    val latitude: String = "",
    val longitude: String = "",
    val title: String = ""
)