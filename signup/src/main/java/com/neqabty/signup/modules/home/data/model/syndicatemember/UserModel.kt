package com.neqabty.signup.modules.home.data.model.syndicatemember

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class UserModel(
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("fullname")
    val fullname: String? = null,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("mobile")
    val mobile: String = "",
    @SerializedName("national_id")
    val nationalId: String = "",
    @SerializedName("entity")
    val entity: Entity
)