package com.neqabty.presentation.entities

import android.os.Parcel
import android.os.Parcelable

data class MemberUI(
    var engineerID: String = "",
    var engineerName: String?,
    var expirationDate: String?,
    var paymentType: String?,
    var billDate: String?,
    var code: Int?,
    var interfaceLanguage: String?,
    var lastPaymentDate: String?,
    var message: String? = "",
    var amount: Int?
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(engineerID)
        parcel.writeString(engineerName)
        parcel.writeString(expirationDate)
        parcel.writeString(paymentType)
        parcel.writeString(billDate)
        parcel.writeInt(code ?: 0)
        parcel.writeString(interfaceLanguage)
        parcel.writeString(lastPaymentDate)
        parcel.writeString(message)
        parcel.writeInt(amount!!)
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