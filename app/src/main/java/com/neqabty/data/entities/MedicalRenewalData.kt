package com.neqabty.data.entities

import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

@Entity(primaryKeys = ["id"])
data class MedicalRenewalData(
        @field:SerializedName("oldRefId")
        var oldRefId: String? = "",
        @field:SerializedName("engineer")
        var contact: ContactData? = null,
        @field:SerializedName("followerslist")
        var followers: List<FollowerItem>? = null
) : Response() {

    data class ContactData(
            @field:SerializedName("BENNAME")
            var name: String = "",
            @field:SerializedName("SyndicateName")
            var syndicateName: String? = "",
            @field:SerializedName("isHealthCareSubscribed")
            var isNew: Boolean? = false,
            @field:SerializedName("requestStatus")
            var requestStatus: Int? = 0
    )

    data class FollowerItem(
            @field:SerializedName("BENNAME")
            var name: String = "",
            @field:SerializedName("BenID")
            var id: Int = 0,
            @field:SerializedName("ISDELETED")
            var isDeleted: Boolean = false,
            @field:SerializedName("BIRTHDATE")
            var birthDate: String = "",
            @field:SerializedName("PicBase64")
            var pic: String? = "",
            @field:SerializedName("AttachmentList")
            var attachments: MutableList<String>? = mutableListOf()

    )
}
