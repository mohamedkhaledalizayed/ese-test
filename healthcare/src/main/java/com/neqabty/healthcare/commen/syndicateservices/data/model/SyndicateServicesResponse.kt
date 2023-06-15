package com.neqabty.healthcare.commen.syndicateservices.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class SyndicateServicesResponse(
    @SerializedName("services")
    val syndicateServices: List<SyndicateService> = listOf()
)