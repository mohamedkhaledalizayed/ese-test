package com.neqabty.yodawy.modules.address.domain.params

import com.google.gson.annotations.SerializedName

data class GetUserUseCaseParams(
    val mobileNumber: String,
    val userNumber: String
)
