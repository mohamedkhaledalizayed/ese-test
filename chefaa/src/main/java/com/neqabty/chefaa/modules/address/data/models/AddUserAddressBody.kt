package com.neqabty.chefaa.modules.address.data.models


import com.google.gson.annotations.SerializedName

data class AddUserAddressBody(
    @SerializedName("apartment")
    val apartment: Int = 0,
    @SerializedName("building_no")
    val buildingNo: Int = 0,
    @SerializedName("floor")
    val floor: Int = 0,
    @SerializedName("land_mark")
    val landMark: String = "",
    @SerializedName("lat")
    val lat: String = "",
    @SerializedName("long")
    val long: String = "",
    @SerializedName("phone")
    val phone: String = "",
    @SerializedName("street_name")
    val streetName: String = "",
    @SerializedName("title")
    val title: String = ""
)