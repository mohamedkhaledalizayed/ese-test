package com.neqabty.healthcare.chefaa.orders.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class OrderListRequestBody(
    @SerializedName("phone") val userNumber: String
)
