package com.neqabty.domain.entities

data class NotificationEntity(
        var id: Int = 0,
        var notificationTypeID: Int = 0,
        var notificationType: String?,
        var status: String?,
        var date: String?,
        var time: String?,
        var mobileView: Int?,
        var userNumber: Int,
        var approvalNumber: String?,
        var approvalImage: String?,
        var comment: String?,
        var title: String?,
        var name: String?,
        var trip: String?,
        var regiment: String?,
        var cost: String?,
        var housingType: String?,
        var numChild: String?,
        var phone: String?
)