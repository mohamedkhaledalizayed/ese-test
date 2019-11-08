package com.neqabty.presentation.entities

import android.os.Parcel
import android.os.Parcelable

data class RegisteryUI(
        var statusCode: Int = 0,
        var registryDataID: String? = "",
        var registryEngineerID: String?,
        var engID: String?,
        var contactID: String?,
        var fullName: String?,
        var lastRenewYear: String?,
        var registryTypeID: String?,
        var regDataStatusID: String?,
        var isOwner: Boolean = false,
        var birthDate: String?,
        var mobile: String?,
        var registerOffice: String?
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
            parcel.readString().toBoolean(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(statusCode)
        parcel.writeString(registryDataID)
        parcel.writeString(registryEngineerID)
        parcel.writeString(engID)
        parcel.writeString(contactID)
        parcel.writeString(fullName)
        parcel.writeString(lastRenewYear)
        parcel.writeString(registryTypeID)
        parcel.writeString(regDataStatusID)
//        parcel.writeString(isOwner)
        parcel.writeString(birthDate)
        parcel.writeString(mobile)
        parcel.writeString(registerOffice)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RegisteryUI> {
        override fun createFromParcel(parcel: Parcel): RegisteryUI {
            return RegisteryUI(parcel)
        }

        override fun newArray(size: Int): Array<RegisteryUI?> {
            return arrayOfNulls(size)
        }
    }
}