package com.neqabty.signup.modules.servicessyndicate.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class SignUpBody(
    @SerializedName("email")
    val email: String,
    @SerializedName("entity_code")
    val entityCode: String,
    @SerializedName("mobile")
    val mobile: String,
    @SerializedName("national_id")
    val nationalId: String
)