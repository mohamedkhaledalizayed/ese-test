package com.neqabty.healthcare.sustainablehealth.subscribtions.data.model.updatepackage


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class UpdatePackageModel(
    @SerializedName("data")
    val `data`: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)