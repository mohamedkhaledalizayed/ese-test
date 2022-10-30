package com.neqabty.chefaa.modules.address.data.models

import com.google.gson.annotations.SerializedName

data class GetUserAddressBody(
    @SerializedName("phone")
    val phone: String = ""
)