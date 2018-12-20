package com.neqabty.data.entities

import android.arch.persistence.room.Entity
import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

@Entity(primaryKeys = ["id"])
data class SpecializationData(
        @field:SerializedName("degree_id")
        var id: Int = 0,
        @field:SerializedName("code")
        var code: String?,
        @field:SerializedName("profession")
        var profession: String?
): Response()