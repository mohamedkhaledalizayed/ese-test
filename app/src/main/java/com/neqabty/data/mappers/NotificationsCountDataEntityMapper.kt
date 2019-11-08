package com.neqabty.data.mappers

import com.neqabty.data.entities.AppVersionData
import com.neqabty.data.entities.NotificationsCountData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.AppVersionEntity
import com.neqabty.domain.entities.NotificationsCountEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationsCountDataEntityMapper @Inject constructor() : Mapper<NotificationsCountData, NotificationsCountEntity>() {

    override fun mapFrom(from: NotificationsCountData): NotificationsCountEntity {
        return NotificationsCountEntity(
            notificationsCount = from.notificationsCount
        )
    }
}
