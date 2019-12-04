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
                comment = from.comment,
                cost = from.cost,
                userNumber = from.userNumber,
                housingType = from.housingType,
                title = from.title,
                name = from.name,
                numChild = from.numChild,
                regiment = from.regiment,
                trip = from.trip,
                mobileView = from.mobileView,
                notificationTypeID = from.notificationTypeID,
                phone = from.phone
        )
    }
}
