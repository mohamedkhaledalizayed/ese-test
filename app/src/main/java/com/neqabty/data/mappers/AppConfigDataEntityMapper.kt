package com.neqabty.data.mappers

import com.neqabty.data.entities.AppConfigData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.AppConfigEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppConfigDataEntityMapper @Inject constructor() : Mapper<AppConfigData, AppConfigEntity>() {

    override fun mapFrom(from: AppConfigData): AppConfigEntity {
        return AppConfigEntity(
                appVersion = from.appVersion,
                healthCareStatus = AppConfigEntity.ConfigStatus(from.healthCareStatus.status, from.healthCareStatus.statusMsg?: ""),
                maintenanceStatus = AppConfigEntity.ConfigStatus(from.maintenanceStatus.status, from.maintenanceStatus.statusMsg?: ""),
                editFollowersStatus = AppConfigEntity.ConfigStatus(from.editFollowersStatus.status, from.editFollowersStatus.statusMsg?: ""),
                cardCommission = from.cardCommission,
                posCommission = from.posCommission,
                fawryCommission = from.fawryCommission,
                minCommission = from.minCommission,
                hasQuestionnaire = from.hasQuestionnaire,
                yodawyConfig = AppConfigEntity.YodawyStatus(from.yodawyConfig.status, from.yodawyConfig.url, from.yodawyConfig.publicKey)
        )
    }
}
