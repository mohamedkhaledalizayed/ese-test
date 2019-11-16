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
                notificationType = from.notificationType,
                id = from.id,
                date = from.date,
                time = from.time,
                status = from.status,
                approvalImage = from.approvalImage,
                approvalNumber = from.approvalNumber,
                area = from.area,
                comment = from.comment,
                degree = from.degree,
                doc1 = from.doc1,
                profession = from.profession,
                providerName = from.providerName,
                userNumber = from.userNumber,
                approvalAmountCost = from.approvalAmountCost,
                housingType = from.housingType,
                name = from.name,
                numChild = from.numChild,
                regiment = from.regiment,
                trip = from.trip,
                mobileView = from.mobileView,
                detailsDate = from.detailsDate,
                detailsTime = from.detailsTime
        )
    }
}
