package com.neqabty.presentation.entities

import android.arch.persistence.room.Entity

data class UserUI(
        var id: Int = 0,
        var fName: String? = "",
        var lName: String? = "",
        var email: String? = "",
        var password: String? = "",
        var number: String? = "",
        var mainSyndicateId: String? = ""
)