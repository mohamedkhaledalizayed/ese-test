package com.neqabty.healthcare.sustainablehealth.search.domain.entity.search

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class ProvidersEntity(
    val address: String,
    val price: String?,
    val area: AreaEntity?,
    val degree: DegreeEntity?,
    val email: String?,
    val governorate: GovernorateEntity,
    val id: Int,
    val image: String?,
    val name: String,
    val phone: String,
    val mobile: String?,
    val notes: String,
    val profession: ProfessionEntity?,
    val serviceProviderType: ServiceProviderTypeEntity?
) : Parcelable