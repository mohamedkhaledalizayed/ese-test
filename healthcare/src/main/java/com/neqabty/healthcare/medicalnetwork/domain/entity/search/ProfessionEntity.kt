package com.neqabty.healthcare.medicalnetwork.domain.entity.search

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class ProfessionEntity(
    val id: Int,
    val professionName: String
) : Parcelable