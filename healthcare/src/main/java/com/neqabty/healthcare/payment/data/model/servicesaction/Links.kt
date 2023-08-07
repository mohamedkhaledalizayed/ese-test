package com.neqabty.healthcare.payment.data.model.servicesaction


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Links(
    @SerializedName("entity")
    val entity: String
)