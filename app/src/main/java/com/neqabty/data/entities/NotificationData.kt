package com.neqabty.data.entities

import android.arch.persistence.room.Entity
import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

@Entity(primaryKeys = ["id"])
data class NotificationData(
        @field:SerializedName("notification_type")
        var notificationType: Int = 0,
        @field:SerializedName("status_name")
        var status: String?,
        @field:SerializedName("request_id")
        var id: Int = 0,
        @field:SerializedName("name")
        var name: String?,
        @field:SerializedName("approval_number")
        var approvalNumber: String?,
        @field:SerializedName("approval_image")
        var approvalImage: String?,
        @field:SerializedName("comment")
        var comment: String?,
        @field:SerializedName("created_at")
        var date: String?,
        @field:SerializedName("updated_at")
        var time: String?,
        @field:SerializedName("syndicate_user_number")
        var userNumber: Int = 0,
        @field:SerializedName("profession_id")
        var profession: String?,
        @field:SerializedName("degree_id")
        var degree: String?,
        @field:SerializedName("area")
        var area: String?,
        @field:SerializedName("provider_name")
        var providerName: String?,
        @field:SerializedName("doc1")
        var doc1: String?,
        @field:SerializedName("trip")
        var trip: String?,
        @field:SerializedName("regiment")
        var regiment: String?,
        @field:SerializedName("approval_ammount_cost")
        var approvalAmountCost: String?,
        @field:SerializedName("housing_type")
        var housingType: String?,
        @field:SerializedName("num_child")
        var numChild: String?,
        @field:SerializedName("mobile_view")
        var mobileView: Int?
) : Response()