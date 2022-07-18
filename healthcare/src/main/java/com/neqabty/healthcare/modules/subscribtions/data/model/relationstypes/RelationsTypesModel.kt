package com.neqabty.healthcare.modules.subscribtions.data.model.relationstypes


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class RelationsTypesModel(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)