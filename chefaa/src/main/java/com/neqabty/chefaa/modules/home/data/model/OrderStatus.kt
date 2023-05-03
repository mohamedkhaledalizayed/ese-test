package com.neqabty.chefaa.modules.home.data.model


import com.google.gson.annotations.SerializedName

data class OrderStatus(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("title_ar")
    val titleAr: String = "",
    @SerializedName("title_en")
    val titleEn: String? = null
)