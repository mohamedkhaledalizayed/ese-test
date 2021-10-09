package com.neqabty.data.entities

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

data class AppConfigData(
        @field:SerializedName("android_version")
        var appVersion: String,
        @field:SerializedName("medicalPayment")
        var healthCareStatus: ConfigStatus,
        @field:SerializedName("maintenance")
        var maintenanceStatus: ConfigStatus,
        @field:SerializedName("questionnaires")
        var hasQuestionnaire: Boolean = false
) : Response() {

    data class ConfigStatus(
            @field:SerializedName("status")
            var status: String,
            @field:SerializedName("status_message")
            var statusMsg: String
    )
}