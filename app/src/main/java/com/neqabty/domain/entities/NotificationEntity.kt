package com.neqabty.domain.entities

data class NotificationEntity(
    var id: Int = 0,
    var subSyndicateId: String?,
    var userNumber: String?,
    var professionID: String?,
    var status: String?,
    var createdAt: String?,
    var approvalNumber: String?,
    var approvalImage: String?,
    var doctor: String?,
    var provider: String?,
    var comment: String?,
    var doc1: String?
)