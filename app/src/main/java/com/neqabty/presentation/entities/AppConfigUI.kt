package com.neqabty.presentation.entities


data class AppConfigUI(
        var appVersion: String,
        var healthCareStatus: ConfigStatus,
        var maintenanceStatus: ConfigStatus,
        var editFollowersStatus: ConfigStatus,
        var cardCommission: Double,
        var posCommission: Double,
        var fawryCommission: Double,
        var minCommission: Double,
        var hasQuestionnaire: Boolean = false,
        var isSyndicatesListEnabled: Boolean = false,
        var yodawyConfig: YodawyStatus,
        var vezeetaConfig: VezeetaStatus
) {

        data class ConfigStatus(
                var status: String,
                var statusMsg: String
        )

        data class YodawyStatus(
                var status: Boolean,
                var url: String,
                var publicKey: String,
                var totalAmount: Boolean,
                var deliverySentence: String
        )

        data class VezeetaStatus(
                var status: Boolean,
                var url: String
        )
}