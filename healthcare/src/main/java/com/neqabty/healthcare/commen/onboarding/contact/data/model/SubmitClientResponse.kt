package com.neqabty.healthcare.commen.onboarding.contact.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class SubmitClientResponse(
    @SerializedName("message")
    val message: String
)