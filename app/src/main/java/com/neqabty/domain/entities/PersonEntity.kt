package com.neqabty.domain.entities

import android.os.Parcel
import android.os.Parcelable

data class PersonEntity(
    var name: String = "",
    var relationship: String = "",
    var birthDate: String = "",
    var ageOnTrip: Int = 0
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readInt())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(ageOnTrip)
        parcel.writeString(name)
        parcel.writeString(birthDate)
        parcel.writeString(relationship)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PersonEntity> {
        override fun createFromParcel(parcel: Parcel): PersonEntity {
            return PersonEntity(parcel)
        }

        override fun newArray(size: Int): Array<PersonEntity?> {
            return arrayOfNulls(size)
        }
    }
}