package com.neqabty.data.entities

import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

@Entity(primaryKeys = ["message"])
data class UpdateUserData(
    @field:SerializedName("message")
    var message: String
) : Response()