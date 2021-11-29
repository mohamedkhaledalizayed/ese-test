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
        @field:SerializedName("edit_followers")
        var editFollowersStatus: ConfigStatus,
        @field:SerializedName("card_payment_commission")
        var cardCommission: Double,
        @field:SerializedName("fawry_payment_commission")
        var fawryCommission: Double,
        @field:SerializedName("min_commission")
        var minCommission: Double,
        @field:SerializedName("questionnaires")
        var hasQuestionnaire: Boolean = false,
        @field:SerializedName("yodawy")
        var yodawyConfig: YodawyStatus
) : Response() {

        data class ConfigStatus(
                @field:SerializedName("status")
                var status: String,
                @field:SerializedName("status_message")
                var statusMsg: String? = ""
        )


        data class YodawyStatus(
                @field:SerializedName("status")
                var status: Boolean,
                @field:SerializedName("url")
                var url: String,
                @field:SerializedName("publicKey")
                var publicKey: String
        )
}