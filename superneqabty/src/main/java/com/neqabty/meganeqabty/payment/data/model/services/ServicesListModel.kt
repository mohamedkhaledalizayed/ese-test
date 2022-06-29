package com.neqabty.meganeqabty.payment.data.model.services


import com.google.gson.annotations.SerializedName

data class ServicesListModel(
    @SerializedName("services")
    val services: List<ServiceModel>
)