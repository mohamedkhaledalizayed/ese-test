package com.neqabty.healthcare.commen.onboarding.contact.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class GovResponse(
    @SerializedName("governorate_ar")
    val governorateAr: String,
    @SerializedName("areas")
    val areas: List<AreaResponse>
)

@Keep
data class AreaResponse(
    @SerializedName("area_name")
    val areaName: String
)