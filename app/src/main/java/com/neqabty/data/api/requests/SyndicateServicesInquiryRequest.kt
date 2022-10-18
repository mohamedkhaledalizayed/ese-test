package com.neqabty.data.api.requests

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Request

data class SyndicateServicesInquiryRequest(
    @SerializedName("user_number")
    var userNumber: String = "",
    @SerializedName("user_name")
    var userName: String = "",
    @SerializedName("mobile")
    var mobile: String = "",
    @SerializedName("deliveryLocation")
    var deliveryLocation: Int,
    @SerializedName("deliveryAddress")
    var deliveryAddress: String = "",
    @SerializedName("deliveryPhone")
    var deliveryPhone: String = "",
    @SerializedName("deliveryCountryId")
    var countryId: Int,
    @SerializedName("service_id")
    var serviceId: Int,
    @SerializedName("payment_type")
    var paymentType: String = ""
) : Request()