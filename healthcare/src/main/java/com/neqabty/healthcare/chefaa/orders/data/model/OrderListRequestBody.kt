package com.neqabty.healthcare.chefaa.orders.data.model

import com.google.gson.annotations.SerializedName

data class OrderListRequestBody(
    @SerializedName("phone") val userNumber: String
)
