package com.neqabty.data.entities

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

data class MedicalRenewalPaymentData(
        @field:SerializedName("ResultType")
        var resultType: String = "",
        @field:SerializedName("Id")
        var requestID: String = "",
        @field:SerializedName("Message")
        var msg: String? = "",
        @field:SerializedName("ReturnObject")
        var paymentItem: PaymentItem? = null
) : Response() {
    data class PaymentItem(
            @field:SerializedName("PaymentRequestNumber")
            var paymentRequestNumber: String? = "",
            @field:SerializedName("Value")
            var amount: Double?,
            @field:SerializedName("PaymentRequestName")
            var name: String?,
            @field:SerializedName("EngineerName")
            var engName: String?,
            @field:SerializedName("OldRefId")
            var engNumber: String?,
            @field:SerializedName("PaymentRequestDetailsDescriptionList")
            var paymentDetailsItems: List<PaymentDetailsItem>? = null
    )

    data class PaymentDetailsItem(
            @field:SerializedName("Name")
            var name: String = "",
            @field:SerializedName("TotalPrice")
            var totalAmount: String = ""
    )
}