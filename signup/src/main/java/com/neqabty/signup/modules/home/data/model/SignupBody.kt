package com.neqabty.signup.modules.home.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class SignupBody(
    @SerializedName("entity_code")
    val entityCode: String = "",
    @SerializedName("email")
    val email: String = "",
    @SerializedName("membership_id")
    val membershipId: String = "",
    @SerializedName("mobile")
    val mobile: String = "",
    @SerializedName("national_id")
    val nationalId: String = ""
)