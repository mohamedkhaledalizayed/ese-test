package com.neqabty.data.entities

import android.arch.persistence.room.Entity
import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

@Entity(primaryKeys = ["id"])
data class InquireUpdateUserData(
    @field:SerializedName("OldRefID")
    var oldRefID: String = "",
    @field:SerializedName("name")
    var fullName: String?,
    @field:SerializedName("address")
    var address: String?,
    @field:SerializedName("phone")
    var phone: String?,
    @field:SerializedName("mobile")
    var mobile: String?,
    @field:SerializedName("email")
    var email: String?,
    @field:SerializedName("birthdate")
    var birthdate: String?,
    @field:SerializedName("graduationyear")
    var graduationyear: String?,
    @field:SerializedName("PassportNumber")
    var passportNumber: String?,
    @field:SerializedName("NationalNumber")
    var nationalID: String?
) : Response()