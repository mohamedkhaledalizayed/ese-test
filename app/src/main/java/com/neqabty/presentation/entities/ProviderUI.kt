package com.neqabty.presentation.entities

import android.os.Parcel
import android.os.Parcelable

data class ProviderUI(
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
        var updatedAt: String?,
        var type: String?
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
            parcel.readString(),
            parcel.readString()) {
    }

    override fun toString(): String {
        return name ?: ""
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
        parcel.writeString(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProviderUI> {
        override fun createFromParcel(parcel: Parcel): ProviderUI {
            return ProviderUI(parcel)
        }

        override fun newArray(size: Int): Array<ProviderUI?> {
            return arrayOfNulls(size)
        }
    }
}