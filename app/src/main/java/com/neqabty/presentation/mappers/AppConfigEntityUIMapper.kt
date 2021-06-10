package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.AppConfigEntity
import com.neqabty.presentation.entities.AppConfigUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppConfigEntityUIMapper @Inject constructor() : Mapper<AppConfigEntity, AppConfigUI>() {

    override fun mapFrom(from: AppConfigEntity): AppConfigUI {
        return AppConfigUI(
                appVersion = from.appVersion,
                healthCareStatus = AppConfigUI.ConfigStatus(from.healthCareStatus.status, from.healthCareStatus.statusMsg),
                maintenanceStatus = AppConfigUI.ConfigStatus(from.maintenanceStatus.status, from.maintenanceStatus.statusMsg)
        )
    }
}
