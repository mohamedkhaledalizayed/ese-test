package com.neqabty.shealth.sustainablehealth.mypackages.data.model.addfollower


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class AddFollowerModel(
    @SerializedName("data")
    val `data`: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)