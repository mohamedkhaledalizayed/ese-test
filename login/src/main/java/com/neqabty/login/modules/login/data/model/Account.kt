package com.neqabty.login.modules.login.data.model


import com.google.gson.annotations.SerializedName

data class Account(
    @SerializedName("email")
    val email: String?,
    @SerializedName("entity")
    val entity: String?,
    @SerializedName("fullname")
    val fullname: String,
    @SerializedName("groups")
    val groups: List<Any>,
    @SerializedName("image")
    val image: String?,
    @SerializedName("is_superuser")
    val isSuperuser: Boolean,
    @SerializedName("mobile")
    val mobile: String,
    @SerializedName("national_id")
    val nationalId: String
)