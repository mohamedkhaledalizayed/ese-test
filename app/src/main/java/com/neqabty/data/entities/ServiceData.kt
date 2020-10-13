package com.neqabty.data.entities

import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

@Entity(primaryKeys = ["id"])
data class ServiceData(
    @field:SerializedName("id")
    var id: Int = 0,
    @field:SerializedName("service_title_ar")
    var name: String?,
    @field:SerializedName("cost")
    var cost: Int?
) : Response()