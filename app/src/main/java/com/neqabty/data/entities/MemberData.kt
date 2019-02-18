package com.neqabty.data.entities

import android.arch.persistence.room.Entity
import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

@Entity(primaryKeys = ["id"])
data class MemberData(
        @field:SerializedName("engineerID")
        var engineerID: Int = 0,
        @field:SerializedName("engineerName")
        var engineerName: String?,
        @field:SerializedName("expirationDate")
        var expirationDate: String?,
        @field:SerializedName("paymentType")
        var paymentType: String?,
        @field:SerializedName("BillDate")
        var billDate: String?,
        @field:SerializedName("Code")
        var code: Int?,
        @field:SerializedName("InterfaceLanguage")
        var interfaceLanguage: String?,
        @field:SerializedName("LastPaymentDate")
        var lastPaymentDate: String?,
        @field:SerializedName("Message")
        var message: String?,
        @field:SerializedName("amount")
        var amount: String?
): Response()