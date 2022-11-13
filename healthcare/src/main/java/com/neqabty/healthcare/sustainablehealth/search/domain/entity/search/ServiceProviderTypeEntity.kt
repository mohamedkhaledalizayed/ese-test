package com.neqabty.healthcare.sustainablehealth.search.domain.entity.search

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class ServiceProviderTypeEntity(
    val id: Int,
    val providerTypeAr: String
) : Parcelable