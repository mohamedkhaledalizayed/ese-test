package com.neqabty.signup.modules.verifyphonenumber.data.model.checkotp


import com.google.gson.annotations.SerializedName

data class CheckOTPModel(
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("verified")
    val verified: Boolean
)