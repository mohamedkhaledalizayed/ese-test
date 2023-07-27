package com.neqabty.healthcare.onboarding.contact.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class SubmitClientResponse(
    @SerializedName("Success")
    val success: Boolean
)