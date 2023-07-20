package com.neqabty.healthcare.medicalnetwork.domain.entity.search

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DegreeEntity(
    val degreeName: String,
    val id: Int
) : Parcelable