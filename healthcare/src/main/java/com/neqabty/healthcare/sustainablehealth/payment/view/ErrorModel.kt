package com.neqabty.healthcare.sustainablehealth.payment.view


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class ErrorModel(
    @SerializedName("error")
    val error: String
)