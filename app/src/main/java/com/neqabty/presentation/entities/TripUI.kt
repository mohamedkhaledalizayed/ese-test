package com.neqabty.presentation.entities

import android.os.Parcel
import android.os.Parcelable

data class TripUI(
        var id: Int = 0,
        var img: String?,
        var title: String?,
        var typeId: String?,
        var dateFrom: String?,
        var dateTo: String?,
        var mainSyndicateId: String?,
        var subSyndicateId: String?,
        var governId: String?,
        var desc: String?,
        var price: String?
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