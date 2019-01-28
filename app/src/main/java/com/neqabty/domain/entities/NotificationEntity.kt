package com.neqabty.domain.entities

data class NotificationEntity(
        var id: Int = 0,
        var subSyndicateId: String?,
        var userNumber: String?,
        var professionID: String?,
        var status: String?,
        var doctor: String?,
        var comment: String?,
        var doc1: String?
)