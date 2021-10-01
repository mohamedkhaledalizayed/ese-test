package com.neqabty.presentation.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LiteFollowersListUI (
    var id: String? = "",
    var name: String? = "",
    var relationTypeName: String? = ""
): Parcelable{
    override fun toString(): String {
        return name ?: ""
    }
}