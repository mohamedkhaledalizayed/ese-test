package com.neqabty.healthcare.chefaa.address.data.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class GetUserAddressBody(
    @SerializedName("phone")
    val phone: String = ""
)