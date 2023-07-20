package com.neqabty.healthcare.medicalnetwork.domain.entity

data class ServiceProviderTypeEntity(
    val createdAt: Long ,
    val id: Int ,
    val points: String ,
    val providerTypeAr: String ,
    val providerTypeEn: String ,
    val updatedAt: Long
)