package com.neqabty.healthcare.chefaa.orders.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class OrderStatus(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("title_ar")
    val titleAr: String = "",
    @SerializedName("title_en")
    val titleEn: String? = null
)