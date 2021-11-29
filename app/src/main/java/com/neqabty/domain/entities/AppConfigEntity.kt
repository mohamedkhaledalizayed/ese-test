package com.neqabty.domain.entities

data class AppConfigEntity(
    var appVersion: String,
    var healthCareStatus: ConfigStatus,
    var maintenanceStatus: ConfigStatus,
    var editFollowersStatus: ConfigStatus,
    var cardCommission: Double,
    var fawryCommission: Double,
    var minCommission: Double,
    var hasQuestionnaire: Boolean = false,
    var yodawyConfig: YodawyStatus
) {

    data class ConfigStatus(
        var status: String,
        var statusMsg: String
    )

    data class YodawyStatus(
        var status: Boolean,
        var url: String,
        var publicKey: String
    )
}