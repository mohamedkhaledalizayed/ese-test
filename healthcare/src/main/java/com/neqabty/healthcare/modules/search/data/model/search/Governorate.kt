package com.neqabty.healthcare.modules.search.data.model.search


import com.google.gson.annotations.SerializedName

data class Governorate(
    @SerializedName("governorate_ar")
    val governorateAr: String,
    @SerializedName("governorate_en")
    val governorateEn: String,
    @SerializedName("id")
    val id: Int
)