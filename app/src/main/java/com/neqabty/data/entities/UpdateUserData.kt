package com.neqabty.data.entities

import android.arch.persistence.room.Entity
import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

@Entity(primaryKeys = ["message"])
data class UpdateUserData(
    @field:SerializedName("message")
    var message: String
) : Response()