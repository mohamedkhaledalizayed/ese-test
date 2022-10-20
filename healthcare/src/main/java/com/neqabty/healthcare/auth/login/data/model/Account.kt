package com.neqabty.healthcare.auth.login.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Account(
    @SerializedName("email")
    val email: String,
    @SerializedName("entity")
    val entity: EntityModel,
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
    @SerializedName("verifed_account")
    val verifiedAccount: Boolean,
    @SerializedName("mobile")
    val mobile: String,
    @SerializedName("national_id")
    val nationalId: String?
)