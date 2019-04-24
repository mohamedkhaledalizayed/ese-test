package com.neqabty.presentation.entities

data class UserUI(
    var id: Int = 0,
    var fName: String? = "",
    var lName: String? = "",
    var email: String? = "",
    var password: String? = "",
    var number: String? = "",
    var mainSyndicateId: String? = ""
)