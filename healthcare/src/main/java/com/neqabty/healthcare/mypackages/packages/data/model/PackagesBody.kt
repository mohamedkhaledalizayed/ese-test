package com.neqabty.healthcare.mypackages.packages.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class PackagesBody(
    @SerializedName("mobile")
    val mobile: String
)