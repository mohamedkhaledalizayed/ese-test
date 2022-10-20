package com.neqabty.healthcare.commen.syndicates.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class Links(
    @SerializedName("requirements")
    val requirements: String = "",
    @SerializedName("services")
    val services: String = ""
)