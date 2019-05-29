package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.AppVersionEntity
import com.neqabty.presentation.entities.AppVersionUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppVersionEntityUIMapper @Inject constructor() : Mapper<AppVersionEntity, AppVersionUI>() {

    override fun mapFrom(from: AppVersionEntity): AppVersionUI {
        return AppVersionUI(
                appVersion = from.appVersion
        )
    }
}
