package com.neqabty.healthcare.commen.syndicateservices.domain.entity


data class SyndicateServiceEntity(
    val name: String,
    val code: String,
    val serviceCategory: ServiceCategory?,
    val type: String,
    val price: String,
    val isActive: Boolean,
    val actions: List<String>,
    val links: Links
)

data class ServiceCategory(
    val name: String
)

data class Links(
    val entity: String
)