package com.neqabty.data.entities

import android.arch.persistence.room.Entity
import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

@Entity(primaryKeys = ["appVersion"])
data class AppVersionData(
    @field:SerializedName("value")
    var appVersion: String
) : Response()