package com.neqabty.data.entities

import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

@Entity(primaryKeys = ["notificationsCount"])
data class NotificationsCountData(
    @field:SerializedName("value")
    var notificationsCount: String
) : Response()