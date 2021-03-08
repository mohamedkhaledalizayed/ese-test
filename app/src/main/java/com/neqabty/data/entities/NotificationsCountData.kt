package com.neqabty.data.entities
import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

data class NotificationsCountData(
    @field:SerializedName("value")
    var notificationsCount: String
) : Response()