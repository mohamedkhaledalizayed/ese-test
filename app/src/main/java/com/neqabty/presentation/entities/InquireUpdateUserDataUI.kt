package com.neqabty.presentation.entities

import android.os.Parcel
import android.os.Parcelable

data class InquireUpdateUserDataUI(
        var oldRefID: String = "",
        var fullName: String?,
        var address: String?,
        var phone: String?,
        var mobile: String?,
        var email: String?,
        var birthdate: String?,
        var graduationyear: String?,
        var passportNumber: String?,
        var nationalID: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
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