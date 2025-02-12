package com.neqabty.data.entities
import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response


data class UserData(
        @field:SerializedName("mobile")
        var mobile: String = "",
        @field:SerializedName("type")
        var type: String? = "",
        @field:SerializedName("syndicate")
        var syndicate: String? = "",
        @field:SerializedName("section")
        var section: String? = "",
        @field:SerializedName("syndicate_id")
        var syndicateID: Int = 0,
        @field:SerializedName("section_id")
        var sectionID: Int = 0,
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