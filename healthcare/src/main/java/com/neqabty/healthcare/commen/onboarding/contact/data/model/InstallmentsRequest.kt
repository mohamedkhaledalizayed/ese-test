package com.neqabty.healthcare.commen.onboarding.contact.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class InstallmentsRequest(
    @SerializedName("national_id")
    val nationalId: String,
    @SerializedName("amount")
    val amount: String,
    @SerializedName("tenor")
    val tenor: String
)