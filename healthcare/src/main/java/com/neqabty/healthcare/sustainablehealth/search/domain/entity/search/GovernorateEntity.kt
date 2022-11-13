package com.neqabty.healthcare.sustainablehealth.search.domain.entity.search

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class GovernorateEntity(
    val governorateAr: String,
    val id: Int
) : Parcelable