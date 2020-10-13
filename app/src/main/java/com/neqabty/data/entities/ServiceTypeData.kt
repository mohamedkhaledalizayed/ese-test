package com.neqabty.data.entities

import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

@Entity(primaryKeys = ["id"])
data class ServiceTypeData(
    @field:SerializedName("id")
    var id: Int = 0,
    @field:SerializedName("service_type_ar")
    var type: String?
) : Response()