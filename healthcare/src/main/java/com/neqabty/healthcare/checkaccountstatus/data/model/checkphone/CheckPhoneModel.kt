package com.neqabty.healthcare.checkaccountstatus.data.model.checkphone


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CheckPhoneModel(
    @SerializedName("message")
    val message: String
)