package com.neqabty.data.entities

import android.arch.persistence.room.Entity
import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

@Entity(primaryKeys = ["id"])
data class ServiceData(
    @field:SerializedName("id")
    var id: Int = 0,
    @field:SerializedName("name")
    var name: String?
) : Response()