package com.neqabty.presentation.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AdUI(
    var imgURL: String,
    var id: Int,
    var type: String,
    var url: String
):Parcelable