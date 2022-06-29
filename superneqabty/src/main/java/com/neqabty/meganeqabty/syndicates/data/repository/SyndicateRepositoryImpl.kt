package com.neqabty.meganeqabty.syndicates.data.repository

import com.neqabty.meganeqabty.syndicates.data.model.*
import com.neqabty.meganeqabty.syndicates.data.source.SyndicateDS
import com.neqabty.meganeqabty.syndicates.domain.entity.*
import com.neqabty.meganeqabty.syndicates.domain.repository.SyndicateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SyndicateRepositoryImpl @Inject constructor(private val syndicateDS: SyndicateDS) :
    SyndicateRepository {
    override fun getSyndicates(): Flow<List<SyndicateEntity>> {
        return flow {
            emit(syndicateDS.getSyndicates().map { it.toSyndicateEntity() })
        }
    }
}

fun SyndicateModel.toSyndicateEntity(): SyndicateEntity {
    return SyndicateEntity(
        code,
        createdAt,
        id,
        image ?: "",
        links = links.toLinksEntity(),
        name,
        registrationNotes ?:"",
        requirements = requirements.map { it.toRequirementEntity() },
        services = services.map { it.toServiceEntity() },
        type = type.toTypeEntity(),
        updatedAt
    )
}

private fun Type.toTypeEntity(): TypeEntity {
    return TypeEntity(createdAt, id, name, updatedAt)
}

fun Service.toServiceEntity(): ServiceEntity {
    return ServiceEntity(
        code,
        createdAt,
        domain ?: "",
        id,
        links = links.toLinksXEntity(),
        name,
        requireRegistration,
        updatedAt
    )
}

private fun LinksX.toLinksXEntity(): LinksXEntity {
    return LinksXEntity(entity)
}

fun Requirement.toRequirementEntity(): RequirementEntity {
    return RequirementEntity(
        entity,
        id,
        label ?: Any(),
        name,
        optional,
        type,
        validators?.toValidatorEntity() ?: ValidatorEntity()
    )
}

private fun Validators.toValidatorEntity(): ValidatorEntity {
    return ValidatorEntity(email, id, max, min, minLength, pattern, required)
}

fun Links.toLinksEntity(): LinksEntity {
    return LinksEntity(requirements, services)
}
