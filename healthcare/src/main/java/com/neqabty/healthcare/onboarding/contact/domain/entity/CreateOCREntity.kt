package com.neqabty.healthcare.onboarding.contact.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CreateOCREntity(
    val idFace: String?,
    val idBack: String?
): Parcelable