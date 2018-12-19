package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.SyndicateEntity
import com.neqabty.presentation.entities.SyndicateUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SyndicateEntityUIMapper @Inject constructor() : Mapper<SyndicateEntity, SyndicateUI>() {

    override fun mapFrom(from: SyndicateEntity): SyndicateUI {
        return SyndicateUI(
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
                subSyndicates = from.subSyndicates?.map { syndicateEntity ->  SyndicateEntityUIMapper().mapFrom(syndicateEntity) }
        )
    }
}