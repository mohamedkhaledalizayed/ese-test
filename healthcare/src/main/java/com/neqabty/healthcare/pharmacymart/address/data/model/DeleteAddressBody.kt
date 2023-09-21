package com.neqabty.healthcare.pharmacymart.address.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class DeleteAddressBody(
    @SerializedName("address_id")
    val addressId: String = ""
)