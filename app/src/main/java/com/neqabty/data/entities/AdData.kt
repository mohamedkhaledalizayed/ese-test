package com.neqabty.data.entities
import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

data class AdData(
    @field:SerializedName("img")
    var imgURL: String,
    @field:SerializedName("add_section_id")
    var id: Int
) : Response()