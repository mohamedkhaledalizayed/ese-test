package com.neqabty.data.entities

import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

@Entity(primaryKeys = ["id"])
data class DegreeData(
    @field:SerializedName("degree_id")
    var id: Int = 0,
    @field:SerializedName("profession")
    var profession: String?
) : Response()