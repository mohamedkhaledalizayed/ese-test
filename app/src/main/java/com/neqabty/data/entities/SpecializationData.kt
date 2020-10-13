package com.neqabty.data.entities

import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

@Entity(primaryKeys = ["id"])
data class SpecializationData(
    @field:SerializedName("profession_id")
    var id: Int = 0,
    @field:SerializedName("profession")
    var profession: String?
) : Response()