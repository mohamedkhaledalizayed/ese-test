package com.neqabty.chefaa.modules.home.domain.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RegistrationEntity(
    val status: Boolean,
    val msg: String
) : Parcelable
