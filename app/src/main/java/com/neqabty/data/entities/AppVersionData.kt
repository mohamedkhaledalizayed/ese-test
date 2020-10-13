package com.neqabty.data.entities

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response
import androidx.room.Entity

@Entity(primaryKeys = ["appVersion"])
data class AppVersionData(
    @field:SerializedName("value")
    var appVersion: String
) : Response()