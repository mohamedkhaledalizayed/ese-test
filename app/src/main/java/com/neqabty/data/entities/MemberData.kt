package com.neqabty.data.entities

import android.arch.persistence.room.Entity
import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

@Entity(primaryKeys = ["id"])
data class MemberData(
    @field:SerializedName("requestID")
    var requestID: String = "",
    @field:SerializedName("name")
    var engineerName: String = "",
    @field:SerializedName("number")
    var engineerNumber: String = "",
    @field:SerializedName("total")
    var amount: Int = 0,
    @field:SerializedName("message")
    var msg: String = "",
    @field:SerializedName("details")
    var payments: List<PaymentItem>? = null,
    @field:SerializedName("PaymentCreationRequest")
    var paymentCreationRequest: PaymentCreationRequest? = null
) : Response() {
    data class PaymentItem(
        @field:SerializedName("Quantity")
        var quantity: Int = 0,
        @field:SerializedName("TotalPrice")
        var totalPrice: String?,
        @field:SerializedName("name")
        var name: String?
    )

    data class PaymentCreationRequest(
        @field:SerializedName("sender")
        var sender: Sender? = null,
        @field:SerializedName("SenderRequestNumber")
        var senderRequestNumber: String?,
        @field:SerializedName("ServiceCode")
        var serviceCode: String?,
        @field:SerializedName("SettlementAmounts")
        var settlementAmounts: SettlementAmounts?,
        @field:SerializedName("Currency")
        var currency: String?,
        @field:SerializedName("RequestExpiryDate")
        var requestExpiryDate: String?,
        @field:SerializedName("UserUniqueIdentifier")
        var userUniqueIdentifier: String?,
        @field:SerializedName("publicKey")
        var publicKey: String?
    )

    data class Sender(
        @field:SerializedName("Id")
        var id: String = "",
        @field:SerializedName("Name")
        var name: String?,
        @field:SerializedName("Password")
        var password: String?
    )

    data class SettlementAmounts(
        @field:SerializedName("SettlementAccountCode")
        var settlementAccountCode: String = "",
        @field:SerializedName("Amount")
        var amount: Int = 0
    )
}