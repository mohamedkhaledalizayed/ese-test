package com.neqabty.domain.entities

data class NewsEntity(
        var id: Int = 0,
        var title: String?,
        var img: String?,
        var desc: String?,
        var mainSyndicateId: String?,
        var subSyndicateId: String?,
        var createdBy: String?,
        var updatedBy: String?,
        var createdAt: String?,
        var updatedAt: String?
)