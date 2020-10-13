package com.neqabty.presentation.entities

import android.os.Parcel
import android.os.Parcelable

data class MemberUI(
    var requestID: String = "",
    var engineerName: String = "",
    var engineerNumber: String = "",
    var amount: Int = 0,
    var msg: String = "",
    var payments: List<PaymentItem>? = null,
    var paymentCreationRequest: PaymentCreationRequest? = null
) : Parcelable {
    data class PaymentItem(
        var quantity: Int = 0,
        var totalPrice: String?,
        var name: String?
    )

    data class PaymentCreationRequest(
        var sender: Sender? = null,
        var senderRequestNumber: String?,
        var serviceCode: String?,
        var settlementAmounts: SettlementAmounts?,
        var currency: String?,
        var requestExpiryDate: String?,
        var userUniqueIdentifier: String?,
        var publicKey: String?
    )

    data class Sender(
        var id: String = "",
        var name: String?,
        var password: String?
    )

    data class SettlementAmounts(
        var settlementAccountCode: String = "",
        var amount: Int = 0
    )

    constructor(parcel: Parcel) : this(
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readInt(),
            parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(requestID)
        parcel.writeString(engineerName)
        parcel.writeString(engineerNumber)
        parcel.writeInt(amount!!)
        parcel.writeString(msg)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NewsUI> {
        override fun createFromParcel(parcel: Parcel): NewsUI {
            return NewsUI(parcel)
        }

        override fun newArray(size: Int): Array<NewsUI?> {
            return arrayOfNulls(size)
        }
    }
}