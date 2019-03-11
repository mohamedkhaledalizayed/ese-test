package com.neqabty.data.entities

import android.arch.persistence.room.Entity
import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

@Entity(primaryKeys = ["id"])
data class AreaData(
        @field:SerializedName("area_id")
        var id: Int = 0,
        @field:SerializedName("area_name")
        var name: String?
): Response()