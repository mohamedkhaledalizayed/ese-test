package com.neqabty.healthcare.chefaa.address.data.models


import com.google.gson.annotations.SerializedName

data class AddressModel(
    @SerializedName("address")
    val address: String? = "",
    @SerializedName("apartment_no")
    val apartmentNo: String? = "",
    @SerializedName("area_ar")
    val areaAr: String? = "",
    @SerializedName("area_en")
    val areaEn: String? = "",
    @SerializedName("building_no")
    val buildingNo: String? = "",
    @SerializedName("city_ar")
    val cityAr: String? = "",
    @SerializedName("city_en")
    val cityEn: String? = "",
    @SerializedName("floor_no")
    val floorNo: String? = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("landmark")
    val landmark: String? = "",
    @SerializedName("latitude")
    val latitude: Double? = 0.0,
    @SerializedName("longitude")
    val longitude: Double? = 0.0,
    @SerializedName("title")
    val title: String? = ""
)