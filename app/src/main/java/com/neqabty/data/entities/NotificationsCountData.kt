package com.neqabty.data.entities

import android.arch.persistence.room.Entity
import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

@Entity(primaryKeys = ["notificationsCount"])
data class NotificationsCountData(
    @field:SerializedName("value")
    var notificationsCount: String
) : Response()