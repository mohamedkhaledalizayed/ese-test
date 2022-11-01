package com.neqabty.chefaa.modules.orders.data.model

import com.google.gson.annotations.SerializedName

data class OrderListRequestBody(
    @SerializedName("phone") val userNumber: String
)
