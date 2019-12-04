package com.neqabty.presentation.mappers

import android.content.Context
import com.neqabty.R
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.NotificationEntity
import com.neqabty.presentation.entities.NotificationUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationEntityUIMapper @Inject constructor() : Mapper<NotificationEntity, NotificationUI>() {

    override fun mapFrom(from: NotificationEntity): NotificationUI {
        return NotificationUI(
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
                phone = from.phone,
                isRead = from.mobileView == 1
        )
    }
}