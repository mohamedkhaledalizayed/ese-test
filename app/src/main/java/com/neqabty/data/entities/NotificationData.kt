package com.neqabty.data.entities

import android.arch.persistence.room.Entity
import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

@Entity(primaryKeys = ["id"])
data class NotificationData(
        @field:SerializedName("request_id")
        var id: Int = 0,
        @field:SerializedName("notification_type_id")
        var notificationTypeID: Int = 0,
        @field:SerializedName("notification_type")
        var notificationType: String?,
        @field:SerializedName("status_name")
        var status: String?,
        @field:SerializedName("date")
        var date: String?,
        @field:SerializedName("time")
        var time: String?,
        @field:SerializedName("mobile_view")
        var mobileView: Int?,
        @field:SerializedName("user_number")
        var userNumber: Int,
        @field:SerializedName("approval_number")
        var approvalNumber: String?,
        @field:SerializedName("approval_image")
        var approvalImage: String?,
        @field:SerializedName("comment")
        var comment: String?,
        @field:SerializedName("requester_title")
        var title: String?,
        @field:SerializedName("requester_name")
        var name: String?,
        @field:SerializedName("trip")
        var trip: String?,
        @field:SerializedName("regiment")
        var regiment: String?,
        @field:SerializedName("cost")
        var cost: String?,
        @field:SerializedName("housing_type")
        var housingType: String?,
        @field:SerializedName("num_child")
        var numChild: String?,
        @field:SerializedName("phone")
        var phone: String?
) : Response()