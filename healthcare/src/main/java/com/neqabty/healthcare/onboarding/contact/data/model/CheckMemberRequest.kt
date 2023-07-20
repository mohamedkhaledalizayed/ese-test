package com.neqabty.healthcare.onboarding.contact.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CheckMemberRequest(
    @SerializedName("national_id")
    val nationalId: String
)