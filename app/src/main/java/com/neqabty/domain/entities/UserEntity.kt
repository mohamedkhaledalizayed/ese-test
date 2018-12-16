package com.neqabty.domain.entities

data class UserEntity(
        var id: Int = 0,
        var fName: String?,
        var lName: String?,
        var email: String?,
        var password: String?,
        var number: String?,
        var mainSyndicateId: String?,
        var subSyndicateId: String?,
        var syndicateUserMobile: String?,
        var userToken: String?,
        var verificationCode: String?,
        var createdAt: String?,
        var updatedAt: String?
)