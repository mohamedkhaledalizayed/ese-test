package com.neqabty.healthcare.medicalnetwork.domain.entity

data class MedicalProviderEntity(
    val addedBy: String,
    val address: String,
    val areaEntity: AreaEntity,
    val areaId: Int,
    val createdAt: Long,
    val degreeEntity: DegreeEntity,
    val degreeId: Int,
    val deletedAt: Long,
    val email: String,
    val governorateEntity: GovernorateEntity,
    val governorateId: Int,
    val id: Int,
    val image: String,
    val name: String,
    val phone: String,
    val professionEntity: ProfessionEntity,
    val professionId: Int,
    val serviceProviderTypeEntity: ServiceProviderTypeEntity,
    val serviceProviderTypeId: Int,
    val updatedAt: Long
)