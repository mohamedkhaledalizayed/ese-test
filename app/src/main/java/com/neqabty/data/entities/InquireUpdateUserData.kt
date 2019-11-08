package com.neqabty.data.entities

import android.arch.persistence.room.Entity
import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

@Entity(primaryKeys = ["id"])
data class InquireUpdateUserData(
        @field:SerializedName("id")
        var id: Int = 0,
        @field:SerializedName("OldRefID")
        var oldRefID: String = "",
        @field:SerializedName("full_name")
        var fullName: String?,
        @field:SerializedName("national_id")
        var nationalID: String?,
        @field:SerializedName("national_verified")
        var nationalVerified: String?,
        @field:SerializedName("phone_number")
        var phoneNumber: String?,
        @field:SerializedName("phone_code")
        var phoneCode: String?,
        @field:SerializedName("phone_verified")
        var phoneVerified: String?,
        @field:SerializedName("birthdate")
        var birthdate: String?,
        @field:SerializedName("created_at")
        var createdAt: String?,
        @field:SerializedName("updated_at")
        var updatedAt: String?
) : Response()