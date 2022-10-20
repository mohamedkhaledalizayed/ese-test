package com.neqabty.healthcare.mega.payment.data.model.servicesaction


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class ServiceActionsModel(
    @SerializedName("service_actions")
    val serviceActions: List<ServiceAction>
)