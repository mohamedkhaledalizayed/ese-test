package com.neqabty.healthcare.modules.search.data.model.search


import com.google.gson.annotations.SerializedName

data class ProvidersResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)