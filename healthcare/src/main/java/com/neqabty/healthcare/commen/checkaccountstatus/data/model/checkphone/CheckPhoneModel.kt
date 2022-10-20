package com.neqabty.healthcare.commen.checkaccountstatus.data.model.checkphone


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class CheckPhoneModel(
    @SerializedName("message")
    val message: String
)