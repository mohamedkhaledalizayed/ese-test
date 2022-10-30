package com.neqabty.shealth.sustainablehealth.search.domain.entity.search



data class ProvidersEntity(
    val address: String,
    val area: AreaEntity?,
    val degree: DegreeEntity?,
    val email: String,
    val governorate: GovernorateEntity,
    val id: Int,
    val image: String?,
    val name: String,
    val phone: String,
    val profession: ProfessionEntity?,
    val serviceProviderType: ServiceProviderTypeEntity?
)