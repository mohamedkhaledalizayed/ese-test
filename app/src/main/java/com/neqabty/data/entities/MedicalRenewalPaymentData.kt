package com.neqabty.data.entities

import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

@Entity(primaryKeys = ["id"])
data class MedicalRenewalPaymentData(
    @field:SerializedName("Id")
    var requestID: String = "",
    @field:SerializedName("ReturnObject")
    var paymentItem: PaymentItem? = null
) : Response() {
    data class PaymentItem(
        @field:SerializedName("PaymentRequestNumber")
        var paymentRequestNumber: String = "",
        @field:SerializedName("Value")
        var amount: Int?,
        @field:SerializedName("PaymentRequestName")
        var name: String?
    )
}