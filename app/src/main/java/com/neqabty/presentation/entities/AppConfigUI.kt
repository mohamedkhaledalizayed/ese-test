package com.neqabty.presentation.entities

data class AppConfigUI(
        var appVersion: String,
        var healthCareStatus: ConfigStatus,
        var maintenanceStatus: ConfigStatus
) {

    data class ConfigStatus(
            var status: String,
            var statusMsg: String
    )
}