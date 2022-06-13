package com.neqabty.healthcare.modules.register.presentation.model

import android.net.Uri

data class Follower(
    val id: Int,
    val name: String,
    val image: Uri,
    val nationalId: String,
    val relation: String
)