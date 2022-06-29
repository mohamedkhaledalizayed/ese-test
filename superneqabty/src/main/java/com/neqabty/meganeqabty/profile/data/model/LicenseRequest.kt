package com.neqabty.meganeqabty.profile.data.model


import com.google.gson.annotations.SerializedName

data class LicenseRequest(
    @SerializedName("license")
    val license: String
)