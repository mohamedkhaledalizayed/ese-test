package com.neqabty.domain.entities

data class NotificationEntity(
        var notificationType: Int = 0,
        var id: Int = 0,
        var date: String?,
        var time: String?,
        var userNumber: Int = 0,
        var profession: String?,
        var degree: String?,
        var area: String?,
        var providerName: String?,
        var status: String?,
        var approvalNumber: String?,
        var approvalImage: String?,
        var comment: String?,
        var doc1: String?,
        var trip: String?,
        var regiment: String?,
        var approvalAmountCost: String?,
        var housingType: String?,
        var numChild: String?,
        var name: String?,
        var mobileView: Int?,
        var detailsDate: String?,
        var detailsTime: String?
)