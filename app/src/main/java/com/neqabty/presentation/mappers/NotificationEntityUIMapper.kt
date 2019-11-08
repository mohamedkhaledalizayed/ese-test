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
                type = if(from.notificationType == 1) "مطالبات طبية" else "الرحلات",
                isRead = from.mobileView == 1
        )
    }
}