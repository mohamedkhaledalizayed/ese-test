package com.neqabty.presentation.entities

import android.os.Parcel
import android.os.Parcelable

data class NotificationUI(
        var id: Int = 0,
        var date: String?,
        var time: String?,
        var userNumber: Int = 0,
        var profession: String?,
        var degree: String?,
        var area: String?,
        var providerName: String?,
        var status: String?,
        var approvalNumber: String?,
        var approvalImage: String?,
        var comment: String?,
        var doc1: String?,
        var trip: String?,
        var regiment: String?,
        var approvalAmmountCost: String?,
        var housingType: String?,
        var numChild: String?,
        var name: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(date)
        parcel.writeString(time)
        parcel.writeInt(userNumber)
        parcel.writeString(profession)
        parcel.writeString(degree)
        parcel.writeString(area)
        parcel.writeString(providerName)
        parcel.writeString(status)
        parcel.writeString(approvalNumber)
        parcel.writeString(approvalImage)
        parcel.writeString(comment)
        parcel.writeString(doc1)
        parcel.writeString(trip)
        parcel.writeString(regiment)
        parcel.writeString(approvalAmmountCost)
        parcel.writeString(housingType)
        parcel.writeString(numChild)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NotificationUI> {
        override fun createFromParcel(parcel: Parcel): NotificationUI {
            return NotificationUI(parcel)
        }

        override fun newArray(size: Int): Array<NotificationUI?> {
            return arrayOfNulls(size)
        }
    }
}