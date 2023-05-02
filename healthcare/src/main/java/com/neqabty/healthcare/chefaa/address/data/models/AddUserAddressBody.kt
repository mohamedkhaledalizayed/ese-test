package com.neqabty.healthcare.chefaa.address.data.models


import com.google.gson.annotations.SerializedName

data class AddUserAddressBody(
    @SerializedName("apartment")
    val apartment: String = "",
    @SerializedName("building_no")
    val buildingNo: String = "",
    @SerializedName("floor")
    val floor: String = "",
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