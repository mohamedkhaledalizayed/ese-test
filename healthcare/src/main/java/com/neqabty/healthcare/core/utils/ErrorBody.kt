package com.neqabty.healthcare.core.utils


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ErrorBody(
    @SerializedName("error")
    val error: String,
    @SerializedName("error_key")
    val error_key: Int
)