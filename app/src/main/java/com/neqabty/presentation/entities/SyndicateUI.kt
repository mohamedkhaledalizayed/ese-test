package com.neqabty.presentation.entities

import android.os.Parcel
import android.os.Parcelable

data class SyndicateUI(
    var id: Int = 0,
    var descAr: String?,
    var descEn: String?,
    var parentId: String?,
    var level: String?,
    var address: String?,
    var phone: String?,
    var email: String?,
    var governId: String?,
    var logo: String?,
    var createdBy: String?,
    var updatedBy: String?,
    var createdAt: String?,
    var updatedAt: String?,
    var fax: String?,
    var mobile: String?,
    var subSyndicates: List<SyndicateUI>?
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
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.createTypedArrayList(CREATOR)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(descAr)
        parcel.writeString(descEn)
        parcel.writeString(parentId)
        parcel.writeString(level)
        parcel.writeString(address)
        parcel.writeString(phone)
        parcel.writeString(email)
        parcel.writeString(governId)
        parcel.writeString(logo)
        parcel.writeString(createdBy)
        parcel.writeString(updatedBy)
        parcel.writeString(createdAt)
        parcel.writeString(updatedAt)
        parcel.writeString(fax)
        parcel.writeString(mobile)
        parcel.writeTypedList(subSyndicates)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SyndicateUI> {
        override fun createFromParcel(parcel: Parcel): SyndicateUI {
            return com.neqabty.presentation.entities.SyndicateUI(parcel)
        }

        override fun newArray(size: Int): Array<SyndicateUI?> {
            return arrayOfNulls(size)
        }
    }
}