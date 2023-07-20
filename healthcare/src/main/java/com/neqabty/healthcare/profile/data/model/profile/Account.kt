package com.neqabty.healthcare.profile.data.model.profile


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Account(
    @SerializedName("email")
    val email: String,
    @SerializedName("entity")
    val entity: EntityBody,
    @SerializedName("fullname")
    val fullname: String?,
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
    val nationalId: String
)