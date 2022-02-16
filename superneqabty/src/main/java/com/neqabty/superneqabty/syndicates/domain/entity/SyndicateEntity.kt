package com.neqabty.superneqabty.syndicates.domain.entity

data class SyndicateEntity(
    val code: String = "",
    val createdAt: String = "",
    val id: Int = 0,
    val image: String = "",
    val links: LinksEntity = LinksEntity(),
    val name: String = "",
    val registrationNotes: String = "",
    val requirements: List<RequirementEntity> = listOf(),
    val services: List<ServiceEntity> = listOf(),
    val type: TypeEntity = TypeEntity(),
    val updatedAt: String = ""
)