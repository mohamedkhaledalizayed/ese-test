package com.neqabty.signup.modules.signup.data.model.neqabtymember


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class NeqabtyMemberModel(
    @SerializedName("email")
    val email: String,
    @SerializedName("entity")
    val entity: Entity,
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