package com.neqabty.meganeqabty.payment.data.model.services


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class Links(
    @SerializedName("entity")
    val entity: String
)