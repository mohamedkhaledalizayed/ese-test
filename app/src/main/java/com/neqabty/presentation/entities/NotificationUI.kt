package com.neqabty.presentation.entities

import android.os.Parcel
import android.os.Parcelable

data class NotificationUI(
        var id: Int = 0,
        var subSyndicateId: String?,
        var userNumber: String?,
        var professionID: String?,
        var status: String?,
        var createdAt: String?,
        var approvalNumber: String?,
        var approvalImage: String?,
        var doctor: String?,
        var provider: String?,
        var comment: String?,
        var doc1: String?
):Parcelable {
    constructor(parcel: Parcel) : this(
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
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(subSyndicateId)
        parcel.writeString(userNumber)
        parcel.writeString(professionID)
        parcel.writeString(status)
        parcel.writeString(createdAt)
        parcel.writeString(approvalNumber)
        parcel.writeString(approvalImage)
        parcel.writeString(doctor)
        parcel.writeString(provider)
        parcel.writeString(comment)
        parcel.writeString(doc1)
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