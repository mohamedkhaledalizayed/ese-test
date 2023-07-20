package com.neqabty.healthcare.profile.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class LicenseRequest(
    @SerializedName("license")
    val license: String
)