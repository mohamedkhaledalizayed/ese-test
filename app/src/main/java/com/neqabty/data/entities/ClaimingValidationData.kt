package com.neqabty.data.entities

import android.arch.persistence.room.Entity
import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

@Entity(primaryKeys = ["id"])
data class ClaimingValidationData(
    @field:SerializedName("status_code")
    var code: Int = 0,
    @field:SerializedName("FullName")
    var engineerName: String = "",
    @field:SerializedName("message")
    var message: String?
) : Response()

