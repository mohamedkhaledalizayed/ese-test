package com.neqabty.healthcare.chefaa.address.data.models

import com.google.gson.annotations.SerializedName

data class GetUserAddressBody(
    @SerializedName("phone")
    val phone: String = ""
)