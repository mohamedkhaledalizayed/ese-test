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
        var followers: List<FollowerItem>? = null,
        @field:SerializedName("HealthCareStatus")
        var healthCareStatus: Int? = 0,
        @field:SerializedName("EngineerStatus")
        var engineerStatus: Int? = 0,
        @field:SerializedName("UpdateRequestStatus")
        var requestStatus: Int? = 0,
        @field:SerializedName("RelationTypeList")
        var relations: List<RelationItem>? = null,
        @field:SerializedName("RejectedDescreptionMessage")
        var rejectionMsg: String? = ""
) : Response() {

    data class ContactData(
            @field:SerializedName("BENNAME")
            var name: String? = "",
            @field:SerializedName("SyndicateName")
            var syndicateName: String? = "",
            @field:SerializedName("IsDead")
            var isDead: Boolean? = false,
            @field:SerializedName("ADDRESS")
            var address: String? = "",
            @field:SerializedName("MOBILE")
            var mobile: String? = ""
    )

    data class FollowerItem(
            @field:SerializedName("BENNAME")
            var name: String? = "",
            @field:SerializedName("BenID")
            var id: Int? = 0,
            @field:SerializedName("ISDELETED")
            var isDeleted: Boolean? = false,
            @field:SerializedName("BIRTHDATE")
            var birthDate: String? = "",
            @field:SerializedName("PicBase64")
            var pic: String? = "",
            @field:SerializedName("AttachmentList")
            var attachments: MutableList<String>? = mutableListOf(),
            @field:SerializedName("MOBILE")
            var mobile: String? = null,
            @field:SerializedName("NATIONALID")
            var nationalId: String? = null,
            @field:SerializedName("RELATIONTYPE")
            var relationType: String? = null,
            @field:SerializedName("GENDER")
            var gender: String? = null

    )

    data class RelationItem(
            @field:SerializedName("Id")
            var id: String? = "",
            @field:SerializedName("RelationTypeName")
            var name: String? = ""
    )

}
