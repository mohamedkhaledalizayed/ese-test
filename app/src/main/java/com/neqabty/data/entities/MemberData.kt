package com.neqabty.data.entities

import android.arch.persistence.room.Entity
import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

@Entity(primaryKeys = ["id"])
data class MemberData(
        @field:SerializedName("requestID")
        var requestID: String = "",
        @field:SerializedName("name")
        var engineerName: String?,
        @field:SerializedName("total")
        var amount: Int?,
        @field:SerializedName("message")
        var msg: String?,
        @field:SerializedName("details")
        var payments: List<PaymentItem>? = null
) : Response(){
        data class PaymentItem(
                @field:SerializedName("Quantity")
                var quantity: Int = 0,
                @field:SerializedName("TotalPrice")
                var totalPrice: String?,
                @field:SerializedName("name")
                var name: String?
        )
}