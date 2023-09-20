package com.neqabty.healthcare.pharmacymart.address.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class GetAddressesBody(
    @SerializedName("mobile")
    val phone: String = ""
)