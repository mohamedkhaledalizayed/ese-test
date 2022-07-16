package com.neqabty.healthcare.modules.subscribtions.data.model.relationstypes


import com.google.gson.annotations.SerializedName

data class RelationsTypesModel(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)