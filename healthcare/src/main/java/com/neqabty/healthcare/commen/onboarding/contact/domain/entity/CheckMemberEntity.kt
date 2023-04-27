package com.neqabty.healthcare.commen.onboarding.contact.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CheckMemberEntity(
    val authorized: Boolean,
    val ocrStatus: String?,
    val message: String?
): Parcelable