package com.neqabty.yodawy.modules.orders.data.model.request

import com.google.gson.annotations.SerializedName

data class OrderRequest(
    @SerializedName("AddressId")
    val addressId: String,
    @SerializedName("YodawyId")
    val yodawyId: String,
    @SerializedName("Plan")
    val plan: String,

)