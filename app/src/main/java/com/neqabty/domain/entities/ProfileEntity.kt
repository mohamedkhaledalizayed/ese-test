package com.neqabty.domain.entities

data class ProfileEntity(
    var invitations: Int = 0,
    var code: String?,
    var name: String?,
    var governerate: String?,
    var syndicate: String?,
    var section: String?,
    var image: String?
)