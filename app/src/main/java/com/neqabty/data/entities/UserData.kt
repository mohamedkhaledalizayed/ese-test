package com.neqabty.data.entities

import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

@Entity(primaryKeys = ["id"])
data class UserData(
        @field:SerializedName("mobile")
        var mobile: String = "",
        @field:SerializedName("type")
        var type: String? = "",
        @field:SerializedName("jwt_token")
        var jwt: String? = "",
        @field:SerializedName("details")
        var details: List<UserDetails>? = null
) : Response() {
    data class UserDetails(
            @field:SerializedName("name")
            var name: String?,
            @field:SerializedName("user_number")
            var userNumber: String?
    )
}