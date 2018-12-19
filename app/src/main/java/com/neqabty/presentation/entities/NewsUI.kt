package com.neqabty.presentation.entities

import android.os.Parcel
import android.os.Parcelable

data class NewsUI(
        var id: Int = 0,
        var title: String?,
        var img: String?,
        var desc: String?,
        var mainSyndicateId: String?,
        var subSyndicateId: String?,
        var createdBy: String?,
        var updatedBy: String?,
        var createdAt: String?,
        var updatedAt: String?
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
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(createdBy)
        parcel.writeString(updatedBy)
        parcel.writeString(createdAt)
        parcel.writeString(updatedAt)
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