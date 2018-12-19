package com.neqabty.data.mappers

import com.neqabty.data.entities.SyndicateData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.SyndicateEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SyndicateDataEntityMapper @Inject constructor() : Mapper<SyndicateData, SyndicateEntity>() {

    override fun mapFrom(from: SyndicateData): SyndicateEntity {
        return SyndicateEntity(
                id = from.id,
                updatedAt = from.updatedAt,
                createdAt = from.createdAt,
                email = from.email,
                address = from.address,
                createdBy = from.createdBy,
                descAr = from.descAr,
                descEn = from.descEn,
                fax = from.fax,
                governId = from.governId,
                level = from.level,
                logo = from.logo,
                mobile = from.mobile,
                parentId = from.parentId,
                phone = from.phone,
                updatedBy = from.updatedBy,
                subSyndicates = from.subSyndicates?.map { syndicateData ->  SyndicateDataEntityMapper().mapFrom(syndicateData) }
        )
    }
}
