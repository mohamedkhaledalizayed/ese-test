package com.neqabty.signup.modules.servicessyndicate.data.model.signup


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class SignUpModel(
    @SerializedName("complains")
    val complains: List<Any>,
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
    @SerializedName("is_superuser")
    val isSuperuser: Boolean,
    @SerializedName("mobile")
    val mobile: String,
    @SerializedName("national_id")
    val nationalId: String,
    @SerializedName("token")
    val token: Token,
    @SerializedName("verifed_account")
    val verifedAccount: Boolean
)