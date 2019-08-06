package com.neqabty.domain.entities

data class NotificationEntity(
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
        var approvalAmmountCost: String?,
        var housingType: String?,
        var numChild: String?,
        var name: String?
)