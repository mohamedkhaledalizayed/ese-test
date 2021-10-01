package com.neqabty.presentation.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AdUI(
    var imgURL: String,
    var id: Int
):Parcelable