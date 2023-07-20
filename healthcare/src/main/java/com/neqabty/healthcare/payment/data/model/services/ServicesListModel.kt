package com.neqabty.healthcare.payment.data.model.services


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ServicesListModel(
    @SerializedName("services")
    val services: List<ServiceModel>
)