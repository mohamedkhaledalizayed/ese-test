package com.neqabty.signup.modules.verifyphonenumber.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class SendOTPBody(
    @SerializedName("phone_number")
    val phoneNumber: String
)