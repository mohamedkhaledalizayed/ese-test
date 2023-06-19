package com.neqabty.healthcare.commen.onboarding.contact.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class InstallmentsResponse(
    @SerializedName("authorized")
    val authorized: Boolean,
    @SerializedName("ocr_status")
    val ocrStatus: String?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("ocrs")
    val ocrs: List<Ocr>
)