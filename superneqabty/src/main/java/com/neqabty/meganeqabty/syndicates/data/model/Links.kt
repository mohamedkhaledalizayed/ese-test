package com.neqabty.meganeqabty.syndicates.data.model


import com.google.gson.annotations.SerializedName

data class Links(
    @SerializedName("requirements")
    val requirements: String = "",
    @SerializedName("services")
    val services: String = ""
)