package com.neqabty.presentation.ui.medicalCategories

import android.os.Parcel
import android.os.Parcelable

data class MedicalCategoryUI(
        var id :Int= 0,
        var name : String,
        var image : Int
):Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeInt(image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MedicalCategoryUI> {
        override fun createFromParcel(parcel: Parcel): MedicalCategoryUI {
            return MedicalCategoryUI(parcel)
        }

        override fun newArray(size: Int): Array<MedicalCategoryUI?> {
            return arrayOfNulls(size)
        }
    }
}