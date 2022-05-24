package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.RefundEntity
import com.neqabty.presentation.entities.RefundUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RefundEntityUIMapper @Inject constructor() : Mapper<RefundEntity, RefundUI>() {

    override fun mapFrom(from: RefundEntity): RefundUI {
        return RefundUI(
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
