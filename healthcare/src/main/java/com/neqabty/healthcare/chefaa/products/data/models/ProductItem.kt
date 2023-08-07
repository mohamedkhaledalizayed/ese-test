package com.neqabty.healthcare.chefaa.products.data.models


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ProductItem(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("image")
    val image: String = "",
    @SerializedName("price")
    val price: Double = 0.0,
    @SerializedName("title_ar")
    val titleAr: String = "",
    @SerializedName("title_en")
    val titleEn: String = "",
    @SerializedName("type")
    val type: String = ""
)