package com.neqabty.presentation.entities

import android.os.Parcel
import android.os.Parcelable

data class MedicalProviderUI(
        var id: Int = 0,
        var name: String?,
        var governId: String?,
        var areaId: String?,
        var address: String?,
        var phones: String?,
        var emails: String?,
        var createdBy: String?,
        var updatedBy: String?,
        var createdAt: String?,
        var updatedAt: String?
): Parcelable {
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

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(governId)
        parcel.writeString(areaId)
        parcel.writeString(address)
        parcel.writeString(phones)
        parcel.writeString(emails)
        parcel.writeString(createdBy)
        parcel.writeString(updatedBy)
        parcel.writeString(createdAt)
        parcel.writeString(updatedAt)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MedicalProviderUI> {
        override fun createFromParcel(parcel: Parcel): MedicalProviderUI {
            return MedicalProviderUI(parcel)
        }

        override fun newArray(size: Int): Array<MedicalProviderUI?> {
            return arrayOfNulls(size)
        }
    }
}