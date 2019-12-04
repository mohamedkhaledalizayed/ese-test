package com.neqabty.data.entities

import android.arch.persistence.room.Entity
import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

@Entity(primaryKeys = ["id"])
data class UserData(
    @field:SerializedName("token")
    var token: String = "",

    @field:SerializedName("type") //
    var type: String? = "",

    @field:SerializedName("name") //
    var name: String? = ""
) : Response()