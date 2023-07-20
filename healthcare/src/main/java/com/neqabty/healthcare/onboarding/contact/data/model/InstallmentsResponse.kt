package com.neqabty.healthcare.onboarding.contact.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class InstallmentsResponse(
    @SerializedName("tenor")
    val tenor: String,
    @SerializedName("max_tenor")
    val maxTenor: String,
    @SerializedName("instalment_value")
    val instalmentValue: String
)