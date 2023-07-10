package com.neqabty.healthcare.commen.mypackages.packages.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class PackagesBody(
    @SerializedName("mobile")
    val mobile: String
)