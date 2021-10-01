package com.neqabty.data.entities

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response


data class LiteFollowersListData(
        @field:SerializedName("Id")
        var id: String? = "",
        @field:SerializedName("Name")
        var name: String? = "",
        @field:SerializedName("RelationTypeName")
        var relationTypeName: String? = ""
) : Response()
