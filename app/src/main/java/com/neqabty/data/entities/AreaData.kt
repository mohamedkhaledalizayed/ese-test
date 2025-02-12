package com.neqabty.data.entities
import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

data class AreaData(
    @field:SerializedName("area_id")
    var id: Int = 0,
    @field:SerializedName("area_name")
    var name: String?,
    @field:SerializedName("gov_id")
    var govId: Int?
) : Response()