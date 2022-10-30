package com.neqabty.shealth.sustainablehealth.checkaccountstatus.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class CheckPhoneBody(
    @SerializedName("mobile")
    val mobile: String
)