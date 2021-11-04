package com.neqabty.domain.entities

data class AppConfigEntity(
    var appVersion: String,
    var healthCareStatus: ConfigStatus,
    var maintenanceStatus: ConfigStatus,
    var editFollowersStatus: ConfigStatus,
    var cardCommission: Double,
    var fawryCommission: Double,
    var hasQuestionnaire: Boolean = false
) {

    data class ConfigStatus(
            var status: String,
            var statusMsg: String
    )
}