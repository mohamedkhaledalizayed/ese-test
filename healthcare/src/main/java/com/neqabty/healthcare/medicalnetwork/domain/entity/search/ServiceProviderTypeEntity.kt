package com.neqabty.healthcare.medicalnetwork.domain.entity.search

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class ServiceProviderTypeEntity(
    val id: Int,
    val providerTypeAr: String,
    val providerTypeEn: String
) : Parcelable