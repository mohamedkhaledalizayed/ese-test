package com.neqabty.healthcare.onboarding.contact.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SubmitClientEntity(
    val success: Boolean
): Parcelable