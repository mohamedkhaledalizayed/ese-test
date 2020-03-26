package com.neqabty.presentation.entities

import android.os.Parcel
import android.os.Parcelable

data class MemberUI(
        var requestID: String = "",
        var engineerName: String?,
        var amount: Int?,
        var msg: String?,
        var payments: List<PaymentItem>? = null
) : Parcelable {
    data class PaymentItem(
            var quantity: Int = 0,
            var totalPrice: String?,
            var name: String?
    )

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString()
    )
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(requestID)
        parcel.writeString(engineerName)
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