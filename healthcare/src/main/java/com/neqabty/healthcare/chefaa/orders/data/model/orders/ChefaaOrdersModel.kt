package com.neqabty.healthcare.chefaa.orders.data.model.orders


import androidx.annotation.Keep

@Keep
data class ChefaaOrdersModel(
    val data: DataModel?,
    val message: String,
    val status: Boolean,
    val status_code: Int
)