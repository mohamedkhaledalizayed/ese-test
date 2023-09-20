package com.neqabty.healthcare.pharmacymart.address.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class AddAddressBody(
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
    @SerializedName("mobile")
    val mobile: String = "",
    @SerializedName("street_name")
    val streetName: String = "",
    @SerializedName("title")
    val title: String = ""
)