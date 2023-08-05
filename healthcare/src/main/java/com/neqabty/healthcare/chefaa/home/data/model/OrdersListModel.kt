package com.neqabty.healthcare.chefaa.home.data.model


import androidx.annotation.Keep

@Keep
data class OrdersListModel(
    val `data`: DataModel?,
    val message: String,
    val status: Boolean,
    val status_code: Int
)