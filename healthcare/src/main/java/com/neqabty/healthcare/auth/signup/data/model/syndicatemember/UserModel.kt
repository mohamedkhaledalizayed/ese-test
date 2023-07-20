package com.neqabty.healthcare.auth.signup.data.model.syndicatemember


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class UserModel(
    @SerializedName("email")
    val email: String,
    @SerializedName("entity")
    val entity: Entity,
    @SerializedName("fullname")
    val fullname: String,
    @SerializedName("groups")
    val groups: List<Any>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String?,
    @SerializedName("is_superuser")
    val isSuperuser: Boolean,
    @SerializedName("mobile")
    val mobile: String,
    @SerializedName("national_id")
    val nationalId: String?,
    @SerializedName("token")
    val token: Token
)