package com.neqabty.data.entities

import android.arch.persistence.room.Entity
import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

@Entity(primaryKeys = ["code"])
data class VerifyUserData(
    @field:SerializedName("phone_code")
    var code: String
) : Response()