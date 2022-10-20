package com.neqabty.healthcare.core.utils


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class ErrorBody(
    @SerializedName("error")
    val error: String,
    @SerializedName("error_key")
    val error_key: Int
)