package com.neqabty.domain.entities

import android.os.Parcel
import android.os.Parcelable

data class AttachmentEntity(
    var fileName: String = "",
    var fileBase64: String = ""
): Parcelable {
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(fileName)
        parcel.writeString(fileBase64)
    }
}