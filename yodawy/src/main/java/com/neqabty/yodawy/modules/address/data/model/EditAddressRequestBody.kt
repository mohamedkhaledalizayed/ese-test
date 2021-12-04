package com.neqabty.yodawy.modules.address.data.model

import com.google.gson.annotations.SerializedName

data class EditAddressRequestBody(
    @SerializedName("address_id")
    val address_id:String,
    @SerializedName("mobile")
    val mobile:String,
    @SerializedName("AddressAlias")
    val addressAlias:String,
    @SerializedName("Address")
    val address:String,
    @SerializedName("Floor")
    val floor:String,
    @SerializedName("Building")
    val building:String,
    @SerializedName("Apartment")
    val apartment:String,
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("long")
    val long: Double,
    @SerializedName("Landmark")
    val landmark:String
)
