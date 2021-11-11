package com.neqabty.yodawy.modules.address.data.model

import com.google.gson.annotations.SerializedName

data class AddAddressRequestBody(
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
    @SerializedName("Landmark")
    val landmark:String
)
