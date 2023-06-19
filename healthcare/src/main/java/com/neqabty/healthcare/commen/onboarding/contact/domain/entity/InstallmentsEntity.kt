package com.neqabty.healthcare.commen.onboarding.contact.domain.entity


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class InstallmentsEntity(
    @SerializedName("authorized")
    val authorized: Boolean,
    @SerializedName("ocr_status")
    val ocrStatus: String?,
    @SerializedName("message")
    val message: String?
)