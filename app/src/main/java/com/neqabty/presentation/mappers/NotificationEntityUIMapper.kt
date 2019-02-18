package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.NotificationEntity
import com.neqabty.presentation.entities.NotificationUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationEntityUIMapper @Inject constructor() : Mapper<NotificationEntity, NotificationUI>() {

    override fun mapFrom(from: NotificationEntity): NotificationUI {
        return NotificationUI(
                id = from.id,
                subSyndicateId = from.subSyndicateId,
                comment = from.comment,
                doc1 = from.doc1,
                professionID = from.professionID,
                status = from.status,
                createdAt = from.createdAt,
                approvalNumber = from.approvalNumber,
                approvalImage = from.approvalImage,
                doctor = from.doctor,
                provider = from.provider,
                userNumber = from.userNumber
        )
    }
}