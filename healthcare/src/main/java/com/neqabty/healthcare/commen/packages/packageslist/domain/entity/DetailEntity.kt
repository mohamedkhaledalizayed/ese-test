package com.neqabty.healthcare.commen.packages.packageslist.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailEntity(
    val cardId: String,
    val description: String,
    val id: Int,
    val title: String
) : Parcelable