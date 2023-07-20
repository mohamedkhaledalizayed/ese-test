package com.neqabty.healthcare.clinido.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Data(
    @SerializedName("url")
    val url: String
)