package com.neqabty.presentation.entities

import android.os.Parcel
import android.os.Parcelable

data class InquireUpdateUserDataUI(
        var id: Int = 0,
        var oldRefID: String = "",
        var fullName: String?,
        var nationalID: String?,
        var nationalVerified: String?,
        var phoneNumber: String?,
        var phoneCode: String?,
        var phoneVerified: String?,
        var birthdate: String?,
        var createdAt: String?,
        var updatedAt: String?
) : Parcelable {
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
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {}

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<InquireUpdateUserDataUI> {
        override fun createFromParcel(parcel: Parcel): InquireUpdateUserDataUI {
            return InquireUpdateUserDataUI(parcel)
        }

        override fun newArray(size: Int): Array<InquireUpdateUserDataUI?> {
            return arrayOfNulls(size)
        }
    }
}