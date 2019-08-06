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
                approvalAmmountCost = from.approvalAmmountCost,
                housingType = from.housingType,
                name = from.name,
                numChild = from.numChild,
                regiment = from.regiment,
                trip = from.trip
        )
    }
}