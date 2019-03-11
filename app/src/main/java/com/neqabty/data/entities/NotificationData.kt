package com.neqabty.data.entities

import android.arch.persistence.room.Entity
import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

@Entity(primaryKeys = ["id"])
data class NotificationData(
        @field:SerializedName("id")
        var id: Int = 0,
        @field:SerializedName("created_at")
        var createdAt: String?,
        @field:SerializedName("sub_syndicate_id")
        var subSyndicateId: String?,
        @field:SerializedName("syndicate_user_number")
        var userNumber: String?,
        @field:SerializedName("profession_id")
        var professionID: String?,
        @field:SerializedName("approval_number")
        var approvalNumber: String?,
        @field:SerializedName("approval_image")
        var approvalImage: String?,
        @field:SerializedName("status")
        var status: String?,
        @field:SerializedName("doctor")
        var doctor: String?,
        @field:SerializedName("provider_name")
        var provider: String?,
        @field:SerializedName("comment")
        var comment: String?,
        @field:SerializedName("doc1")
        var doc1: String?
): Response()