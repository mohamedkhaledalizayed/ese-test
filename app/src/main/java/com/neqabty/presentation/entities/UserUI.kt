package com.neqabty.presentation.entities

data class UserUI(
        var mobile: String = "",
        var type: String?,
        var syndicate: String?,
        var section: String?,
        var syndicateID: Int = 0,
        var sectionID: Int = 0,
        var jwt: String? = "",
        var details: List<UserDetails>?
) {
    data class UserDetails(
            var name: String?,
            var userNumber: String?
    )
}