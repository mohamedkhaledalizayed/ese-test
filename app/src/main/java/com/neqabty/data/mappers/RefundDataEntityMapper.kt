package com.neqabty.data.mappers

import com.neqabty.data.entities.RefundData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.RefundEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RefundDataEntityMapper @Inject constructor() : Mapper<RefundData, RefundEntity>() {

    override fun mapFrom(from: RefundData): RefundEntity {
        return RefundEntity(
            id = from.id,
            name = from.name,
            mobile = from.mobile,
            userNumber = from.userNumber,
            benId = from.benId,
            description = from.description,
            branchProfileId = from.branchProfileId,
            serviceProviderId = from.serviceProviderId,
            letterTypeId = from.letterTypeId,
            mobileToken = from.mobileToken,
            status = from.status,
            syndicate_request_id = from.syndicate_request_id,
            created_at = from.created_at
        )
    }
}
