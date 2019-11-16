package com.neqabty.data.entities

import android.arch.persistence.room.Entity
import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

@Entity(primaryKeys = ["id"])
data class MemberData(
    @field:SerializedName("OldRefID")
    var engineerID: String = "",
    @field:SerializedName("name")
    var engineerName: String?,
    @field:SerializedName("datee")
    var expirationDate: String?,
    @field:SerializedName("paymentType")
    var paymentType: String?,
    @field:SerializedName("date")
    var billDate: String?,
    @field:SerializedName("Code")
    var code: Int?,
    @field:SerializedName("InterfaceLanguage")
    var interfaceLanguage: String?,
    @field:SerializedName("dateee")
    var lastPaymentDate: String?,
    @field:SerializedName("Message")
    var message: String?,
    @field:SerializedName("total_amount")
    var amount: Int?
) : Response()