package com.neqabty.presentation.entities

import android.os.Parcel
import android.os.Parcelable

data class NotificationUI(
    var id: Int = 0,
    var notificationTypeID: Int = 0,
    var notificationType: String?,
    var status: String?,
    var date: String?,
    var time: String?,
    var mobileView: Int?,
    var userNumber: Int,
    var approvalNumber: String?,
    var approvalImage: String?,
    var comment: String?,
    var title: String?,
    var name: String?,
    var trip: String?,
    var regiment: String?,
    var cost: Int?,
    var housingType: String?,
    var numChild: String?,
    var phone: String?,
    var isRead: Boolean = false
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString().toBoolean()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(notificationTypeID)
        parcel.writeString(notificationType)
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