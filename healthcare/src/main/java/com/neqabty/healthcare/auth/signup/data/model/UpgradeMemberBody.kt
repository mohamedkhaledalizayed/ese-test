package com.neqabty.healthcare.auth.signup.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class UpgradeMemberBody(
    @SerializedName("entity_code")
    val entityCode: String = "",
    @SerializedName("membership_id")
    val membershipId: String = "",
    @SerializedName("national_id")
    val nationalId: String = ""
)