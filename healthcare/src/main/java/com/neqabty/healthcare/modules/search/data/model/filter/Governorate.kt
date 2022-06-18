package com.neqabty.healthcare.modules.search.data.model.filter


import com.google.gson.annotations.SerializedName

data class Governorate(
    @SerializedName("governorate_ar")
    val governorateAr: String,
    @SerializedName("id")
    val id: Int
)