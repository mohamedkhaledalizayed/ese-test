package com.neqabty.domain.entities

data class SyndicateEntity(
        var id: Int = 0,
        var descAr: String?,
        var descEn: String?,
        var parentId: String?,
        var level: String?,
        var address: String?,
        var phone: String?,
        var email: String?,
        var governId: String?,
        var logo: String?,
        var createdBy: String?,
        var updatedBy: String?,
        var createdAt: String?,
        var updatedAt: String?,
        var fax: String?,
        var mobile: String?
)