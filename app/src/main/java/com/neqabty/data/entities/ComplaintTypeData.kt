package com.neqabty.data.entities

import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

@Entity(primaryKeys = ["id"])
data class ComplaintTypeData(
    @field:SerializedName("id")
    var id: Int = 0,
    @field:SerializedName("complaint_type")
    var type: String?
) : Response()