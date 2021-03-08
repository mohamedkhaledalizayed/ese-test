package com.neqabty.data.entities
import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response


data class GovernData(
    @field:SerializedName("governorate_id")
    var id: Int = 0,
    @field:SerializedName("governorate_desc_ar")
    var name: String?
) : Response()