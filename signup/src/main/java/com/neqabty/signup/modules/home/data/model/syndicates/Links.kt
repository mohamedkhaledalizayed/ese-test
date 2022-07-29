package com.neqabty.signup.modules.home.data.model.syndicates


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class Links(
    @SerializedName("requirements")
    val requirements: String
)