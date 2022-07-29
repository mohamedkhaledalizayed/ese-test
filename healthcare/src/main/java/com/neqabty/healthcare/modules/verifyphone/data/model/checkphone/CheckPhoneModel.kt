package com.neqabty.healthcare.modules.verifyphone.data.model.checkphone


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class CheckPhoneModel(
    @SerializedName("message")
    val message: String
)