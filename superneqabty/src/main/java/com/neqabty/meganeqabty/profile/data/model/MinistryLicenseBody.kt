package com.neqabty.meganeqabty.profile.data.model


import com.google.gson.annotations.SerializedName

data class MinistryLicenseBody(
    @SerializedName("license_request")
    val licenseRequest: LicenseRequest
)