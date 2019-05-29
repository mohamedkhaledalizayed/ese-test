package com.neqabty.data.mappers

import com.neqabty.data.entities.AppVersionData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.AppVersionEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppVersionDataEntityMapper @Inject constructor() : Mapper<AppVersionData, AppVersionEntity>() {

    override fun mapFrom(from: AppVersionData): AppVersionEntity {
        return AppVersionEntity(
            appVersion = from.appVersion
        )
    }
}
