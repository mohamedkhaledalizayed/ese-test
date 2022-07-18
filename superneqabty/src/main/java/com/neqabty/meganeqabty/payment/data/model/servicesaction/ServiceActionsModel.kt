package com.neqabty.meganeqabty.payment.data.model.servicesaction


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class ServiceActionsModel(
    @SerializedName("service_actions")
    val serviceActions: List<ServiceAction>
)