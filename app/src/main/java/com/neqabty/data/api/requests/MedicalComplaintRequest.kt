package com.neqabty.data.api.requests

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Request

data class MedicalComplaintRequest(
        @SerializedName("name")
        var name: String = "",
        @SerializedName("user_number")
        var userNumber: String = "",
        @SerializedName("mobile")
        var mobile: String = "",
        @SerializedName("benId")
        var benId: String = "",
        @SerializedName("description")
        var description: String = "",
        @SerializedName("branchProfileId")
        var branchProfileId: String = "",
        @SerializedName("serviceProviderId")
        var serviceProviderId: String = "",
        @SerializedName("letterTypeId")
        var letterTypeId: String = "",
        @SerializedName("attachments")
        var attachments: List<AttachmentItem> = listOf()
) : Request() {
        data class AttachmentItem(
                @SerializedName("fileName")
                var fileName: String = "",
                @SerializedName("fileBase64")
                var fileBase64: String = ""
        )
}
