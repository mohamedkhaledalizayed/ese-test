package com.neqabty.data.mappers

import com.neqabty.data.entities.NotificationData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.NotificationEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationDataEntityMapper @Inject constructor() : Mapper<NotificationData, NotificationEntity>() {

    override fun mapFrom(from: NotificationData): NotificationEntity {
        return NotificationEntity(
                id = from.id,
                subSyndicateId = from.subSyndicateId,
                comment = from.comment,
                doc1 = from.doc1,
                professionID = from.professionID,
                status = from.status,
                doctor = from.doctor,
                userNumber = from.userNumber
        )
    }
}
