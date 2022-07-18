package com.neqabty.healthcare.modules.search.data.model.filter


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class Governorate(
    @SerializedName("governorate_ar")
    val governorateAr: String,
    @SerializedName("id")
    val id: Int
)