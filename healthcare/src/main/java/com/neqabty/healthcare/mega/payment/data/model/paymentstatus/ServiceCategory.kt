package com.neqabty.healthcare.mega.payment.data.model.paymentstatus


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class ServiceCategory(
    @SerializedName("name")
    val name: String
)