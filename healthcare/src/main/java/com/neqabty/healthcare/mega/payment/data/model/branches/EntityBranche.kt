package com.neqabty.healthcare.mega.payment.data.model.branches


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class EntityBranche(
    @SerializedName("address")
    val address: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("entity")
    val entity: EntityModel
)