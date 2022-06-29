package com.neqabty.meganeqabty.payment.data.model.servicesaction


import com.google.gson.annotations.SerializedName

data class ServiceActionsModel(
    @SerializedName("service_actions")
    val serviceActions: List<ServiceAction>
)