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
    var notes: String?,
    var price: String?,
    var imgs: List<String>? = null,
    var regiments: List<TripRegiment>? = null,
    var place: TripPlace? = null
) : Parcelable {

    data class TripRegiment(
        var regimentId: Int = 0,
        var tripId: Int?,
        var dateFrom: String?,
        var dateTo: String?,
        var hotelOnePerson: Int?,
        var hotelTwoPerson: Int?,
        var hotelThreePerson: Int?,
        var viewPrice: Int?,
        var sidePrice: Int?,
        var price: Int?,
        var oneRoom: Int?,
        var twoRooms: Int?,
        var studio: Int?,
        var tripType: String?,
        var createdBy: String?,
        var updatedBy: String?,
        var createdAt: String?,
        var updatedAt: String?
    ) {
        override fun toString(): String {
            return dateFrom ?: "" + " : " + dateTo ?: ""
        }
    }

    data class TripPlace(
        var placeId: Int = 0,
        var details: String?,
        var name: String?
    )

    data class TripRoom(
        var roomId: Int = 0,
        var name: String?,
        var price: Int?
    ) {
        override fun toString(): String {
            return name ?: ""
        }
    }

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
            parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TripUI> {
        override fun createFromParcel(parcel: Parcel): TripUI {
            return TripUI(parcel)
        }

        override fun newArray(size: Int): Array<TripUI?> {
            return arrayOfNulls(size)
        }
    }
}