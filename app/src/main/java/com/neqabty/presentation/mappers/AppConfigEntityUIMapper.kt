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
                maintenanceStatus = AppConfigUI.ConfigStatus(from.maintenanceStatus.status, from.maintenanceStatus.statusMsg),
                editFollowersStatus = AppConfigUI.ConfigStatus(from.editFollowersStatus.status, from.editFollowersStatus.statusMsg),
                cardCommission = from.cardCommission,
                posCommission = from.posCommission,
                fawryCommission = from.fawryCommission,
                minCommission = from.minCommission,
                hasQuestionnaire = from.hasQuestionnaire,
                yodawyConfig = AppConfigUI.YodawyStatus(from.yodawyConfig.status, from.yodawyConfig.url, from.yodawyConfig.publicKey),
                vezeetaConfig = AppConfigUI.VezeetaStatus(from.vezeetaConfig.status, from.vezeetaConfig.url)
        )
    }
}
