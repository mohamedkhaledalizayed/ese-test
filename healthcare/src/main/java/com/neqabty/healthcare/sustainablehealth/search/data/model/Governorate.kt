package com.neqabty.healthcare.sustainablehealth.search.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.util.*
@Keep
data class Governorate(
    @SerializedName("created_at")
    val createdAt: Date? = null ,
    @SerializedName("governorate_ar")
    val governorateAr: String = "",
    @SerializedName("governorate_en")
    val governorateEn: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("updated_at")
    val updatedAt: Date? = null
)