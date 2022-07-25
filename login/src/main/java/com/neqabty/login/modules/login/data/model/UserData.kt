package com.neqabty.login.modules.login.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class UserData(
    @SerializedName("account")
    val account: Account
)